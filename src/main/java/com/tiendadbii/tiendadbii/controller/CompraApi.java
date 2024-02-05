package com.tiendadbii.tiendadbii.controller;

import com.fasterxml.jackson.annotation.JsonView;
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
import com.tiendadbii.tiendadbii.util.swagger_example.CompraExample;
import com.tiendadbii.tiendadbii.util.views.CompraViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compra")
@Tag(name = "2. Compra", description = "Con este EndPoint se puede realizar todas las operaciones de Compra necesarias para registrar e incrementar el stock de los productos")
public class CompraApi {
  private final ICompraService compraService;
  private final ModelMapper modelMapper;
  private final IProveedorService proveedorService;
  private final DetalleCompraRepository detalleCompraRepository;

  @Operation(summary = "Buscar una Compra dado {idCompra}", description = "Retornar una compra desde la base de datos",
    responses = {
      @ApiResponse(responseCode = "200", description = "Compra encontrada exitosamente",
        content = {@Content(examples = @ExampleObject(value = CompraExample.COMPRA_CON_PROVEEDOR_ID))}),
      @ApiResponse(responseCode = "404", description = "Compra no encontrada con el id: {idCompra}",
        content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
    })
  @JsonView(CompraViews.ConProveedorId.class)
  @GetMapping("/{idCompra}")
  public ResponseEntity<?> findById(@PathVariable Integer idCompra) {
    Compra compra = compraService.findById(idCompra);
    if (compra == null) {
      throw new EntityNotFoundException("Compra no encontrada con el id: " + idCompra);
    }
    return ResponseEntity.ok().body(this.toDto(compra));
  }

  @Operation(summary = "Listar todas las compras registradas con formato de página", description = "La búsqueda es procesada en la base de datos, basandose en 4 parametros",
    responses = {
      @ApiResponse(responseCode = "200", description = "Lista de compras registradas", content =
      @Content(examples = @ExampleObject(value = CompraExample.COMPRA_CON_PROVEEDOR_ID))),
    })
  @GetMapping("/pageable")
  @JsonView(CompraViews.ConProveedorId.class)
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

  @Operation(summary = "Crear nueva Compra",
    description = "Las compras son el medio para poder registrar productos e incrementar stock de productos. Existiran 2 ejemplos de como registrar compras de las operaciones que se pueden realizar.",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "En ambos ejemplos se debe agregar el idProveedor en el Endpoint: ",
      content = @Content(
        examples = {
          @ExampleObject(
            summary = "Compra para registrar nuevo producto",
            name = "Compra para registrar nuevo producto",
            value = CompraExample.COMPRA_PARA_REGISTRAR_NUEVO_PRODUCTO,
            description = """
              Se requiere que el objeto tenga al menos un item en el campo "listaDetalleCompra".
              Asegúrate de completar los campos establecidos en el ejemplo, incluido los datos del producto a registrar.
              El stock inicial del producto se establecerá en el campo "cantidad" de dicho item.
              \nEl costo total y descuento de costo total de la compra se calcula internamente.
              """),
          @ExampleObject(
            summary = "Compra para agregar stock de producto registrado",
            name = "Compra sin Producto",
            value = CompraExample.COMPRA_PARA_INCREMENTAR_STOCK_DE_PRODUCTO_REGISTRADO,
            description = """
              Es necesario que el objeto contenga al menos un item en el campo "listaDetalleCompra".
              Asegúrate de completar los campos establecidos en el ejemplo, donde en el campo "producto" solo se requiere llenar el "codigoProducto".
              Para incrementar el stock del producto, debe completa el campo "cantidad" del item en el campo "listaDetalleCompra".
              \nEl costo total y descuento de costo total de la compra se calcula internamente.
              """
          )
        })
    ),
    responses = {
      @ApiResponse(
        responseCode = "201",
        description = "Compra creada exitosamente, se retorna el objeto creado con el header Location: /api/compra/{idCompra}",
        content = @Content(examples = {
          @ExampleObject(
            summary = "Response para POST /api/compra",
            name = "Ejemplo Response para POST /api/compra",
            value = CompraExample.COMPRA_CREADA_EXITOSAMENTE
          )
        })
      ),
      @ApiResponse(responseCode = "400", description = "Petición invalida, asegúrate de completar todos los campos requeridos.", content = {@Content(mediaType = MediaType.TEXT_PLAIN_VALUE)}),
      @ApiResponse(responseCode = "404", description = "Proveedor no encontrado con el id: {idProveedor}", content = {@Content(mediaType = MediaType.TEXT_PLAIN_VALUE)}),
    }
  )
  @JsonView(CompraViews.ConProveedorId.class)
  @PostMapping("/proveedor/{idProveedor}")
  public ResponseEntity<?> createCompra(@RequestBody CompraDto dto, @PathVariable Integer idProveedor) {
    if (proveedorService.findById(idProveedor) == null) {
      throw new EntityNotFoundException("Proveedor no encontrado con el id: " + idProveedor);
    }
    if (dto.getListaDetalleCompra() == null)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Compra debe tener items en el campo \"listaCompra\"");
    for (DetalleCompraDto detalleCompraDto : dto.getListaDetalleCompra()) {
      if (detalleCompraDto.getProducto() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cada item en el campo \"listaCompra\" debe tener el campo \"producto\"");
      }
    }

    Compra compra = this.toEntity(dto);


    Compra compraPersisted = compraService.createNew(compra, idProveedor);
    compraPersisted.setListaDetalleCompra(detalleCompraRepository.findAllByCompra_IdCompra(compraPersisted.getIdCompra()));

    CompraDto compraDto = this.toDto(compraPersisted);

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
