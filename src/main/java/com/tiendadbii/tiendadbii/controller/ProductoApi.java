package com.tiendadbii.tiendadbii.controller;

import com.tiendadbii.tiendadbii.dto.CompraDto;
import com.tiendadbii.tiendadbii.dto.ProductoDto;
import com.tiendadbii.tiendadbii.model.entity.Producto;
import com.tiendadbii.tiendadbii.model.service.interfaces.IProductoService;
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
@RequestMapping("/api/producto")
@Tag(name = "Producto", description = "API Simple operations")
public class ProductoApi {
  private final IProductoService productoService;
  private final ModelMapper modelMapper;

  @Operation(summary = "Find Producto with given codigoProducto", description = "Given an codigoProducto, it will return Producto from DB",
    responses = {
      @ApiResponse(responseCode = "200", description = "Producto found successfully",
        content = {@Content(schema = @Schema(implementation = CompraDto.class))}),
      @ApiResponse(responseCode = "404", description = "Producto not found", content = @Content),
    })
  @GetMapping("/{codigoProducto}")
  public ResponseEntity<ProductoDto> findById(@PathVariable Integer codigoProducto) {
    Producto producto = productoService.findById(codigoProducto);
    if (producto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(this.toDto(producto));
  }

  @Operation(summary = "List all registered Producto with Pageable", description = "The serach is performed in DB using 4 parameters")
  @GetMapping("/pageable")
  public ResponseEntity<List<ProductoDto>> findAll(
    @RequestParam(required = false, defaultValue = "0") int page,
    @RequestParam(required = false, defaultValue = "10") int size,
    @RequestParam(required = false, defaultValue = "nombreProducto") String property,
    @RequestParam(required = false, defaultValue = "ASC") String order
  ) {
    Sort.Direction direction = Sort.Direction.fromString(order);
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, property));

    return ResponseEntity.ok().body(productoService.findAll(pageable).stream().map(this::toDto).toList());
  }

  @Operation(summary = "Update Producto", description = "Update an existing Producto in DB, all fields should be filled",
    responses = {
      @ApiResponse(responseCode = "200", description = "Producto updated successfully",
        content = {@Content(schema = @Schema(implementation = ProductoDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad request, fields should be filled", content = @Content),
    })
  @PutMapping
  public ResponseEntity<?> updateProducto(@RequestBody ProductoDto dto) {
    if (dto.getCodigoBarra() == null || dto.getCodigoBarra().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Producto should have codigoBarra");
    }
    return ResponseEntity.ok().body(this.toDto(productoService.update(this.toEntity(dto))));
  }

  private ProductoDto toDto(Producto entity) {
    return modelMapper.map(entity, ProductoDto.class);
  }

  private Producto toEntity(ProductoDto dto) {
    return modelMapper.map(dto, Producto.class);
  }
}
