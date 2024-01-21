package com.tiendadbii.tiendadbii.controller;

import com.tiendadbii.tiendadbii.dto.CompraDto;
import com.tiendadbii.tiendadbii.dto.DetalleCompraDto;
import com.tiendadbii.tiendadbii.dto.ProveedorDto;
import com.tiendadbii.tiendadbii.model.entity.Compra;
import com.tiendadbii.tiendadbii.model.entity.DetalleCompra;
import com.tiendadbii.tiendadbii.model.entity.Producto;
import com.tiendadbii.tiendadbii.model.entity.Proveedor;
import com.tiendadbii.tiendadbii.model.repository.DetalleCompraRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICompraService;
import com.tiendadbii.tiendadbii.model.service.interfaces.IProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compra")
@Tag(name = "Compra", description = "With this API, you can perform Create and Read Operation")
public class CompraApi {
  private final ICompraService compraService;
  private final ModelMapper modelMapper;
  private final IProveedorService proveedorService;
  private final DetalleCompraRepository detalleCompraRepository;

  @Operation(summary = "Find Compra with given ID", description = "Given an idCompra, it will return Compra from DB",
    responses = {
      @ApiResponse(responseCode = "200", description = "Compra found successfully",
        content = {@Content(schema = @Schema(implementation = CompraDto.class))}),
      @ApiResponse(responseCode = "404", description = "Compra not found", content = @Content),
    })
  @GetMapping("/{idCompra}")
  public ResponseEntity<CompraDto> findById(@PathVariable Integer idCompra) {
    Compra compra = compraService.findById(idCompra);
    if (compra == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(this.toDto(compra));
  }

  @Operation(summary = "List all registered Compra with Pageable", description = "The serach is performed in DB using 4 parameters")
  @GetMapping("/pageable")
  public ResponseEntity<List<CompraDto>> findAll(
    @RequestParam(required = false, defaultValue = "0") int page,
    @RequestParam(required = false, defaultValue = "10") int size,
    @RequestParam(required = false, defaultValue = "fechaCompra") String property,
    @RequestParam(required = false, defaultValue = "DESC") String order
  ) {
    Sort.Direction direction = Sort.Direction.fromString(order);
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, property));

    return ResponseEntity.ok().body(compraService.findAll(pageable).stream().map(this::toDto).toList());
  }

  @Operation(summary = "Create new Compra", description = "To create a new Compra, provide the necessary fields in the request body.",
    responses = {
      @ApiResponse(responseCode = "201", description = "Compra created succesfully, returned Compra",
        content = {@Content(schema = @Schema(implementation = CompraDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad request, invalid field"),
    })
  @PostMapping("/proveedor/{idProveedor}")
  public ResponseEntity<?> createCompra(@RequestBody CompraDto dto, @PathVariable Integer idProveedor) {
    if (dto.getListaDetalleCompra() == null)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Compra must have a list of DetalleCompra");
    for (DetalleCompraDto detalleCompraDto : dto.getListaDetalleCompra()) {
      if (detalleCompraDto.getProducto() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Each detalleCompra in compra must have a producto");
      }
    }

    Compra compra = this.toEntity(dto);


    Compra compraPersisted = compraService.createNew(compra, idProveedor);
    compraPersisted.setListaDetalleCompra(detalleCompraRepository.findAllByCompra_IdCompra(compraPersisted.getIdCompra()));

    CompraDto compraDto = this.toDto(compraPersisted);
    compraDto.setProveedor(null);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .header("Location", "/api/compra/" + compraDto.getIdCompra())
      .body(compraDto);
  }


  private Compra toEntity(CompraDto dto) {
    Compra compra = modelMapper.map(dto, Compra.class);
    if (dto.getProveedor() != null) compra.setProveedor(modelMapper.map(dto.getProveedor(), Proveedor.class));
    if (dto.getListaDetalleCompra() != null) {
      List<DetalleCompra> listaDetalleCompra = dto.getListaDetalleCompra().stream().map(detalleCompraDto -> {
        DetalleCompra detalleCompra = modelMapper.map(detalleCompraDto, DetalleCompra.class);
        if (detalleCompra.getProducto() != null) {
          detalleCompra.setProducto(modelMapper.map(detalleCompraDto.getProducto(), Producto.class));
        }
        compra.getListaDetalleCompra().add(detalleCompra);
        return detalleCompra;
      }).toList();
      compra.setListaDetalleCompra(listaDetalleCompra);
    }

    return compra;
  }

  private CompraDto toDto(Compra entity) {
    CompraDto compraDto = modelMapper.map(entity, CompraDto.class);
    if (entity.getProveedor() != null)
      entity.getProveedor().setListaCompra(null);
    compraDto.setProveedor(modelMapper.map(entity.getProveedor(), ProveedorDto.class));
    if (entity.getListaDetalleCompra() != null) {
      List<DetalleCompraDto> listaDetalleCompraDto = entity.getListaDetalleCompra().stream().map(detalleCompra ->
        modelMapper.map(detalleCompra, DetalleCompraDto.class)).toList();

      compraDto.setListaDetalleCompra(listaDetalleCompraDto);
    }

    return compraDto;
  }
}
