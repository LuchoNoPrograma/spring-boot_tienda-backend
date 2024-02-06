package com.tiendadbii.tiendadbii.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.dto.ProductoDto;
import com.tiendadbii.tiendadbii.model.entity.Producto;
import com.tiendadbii.tiendadbii.model.service.interfaces.IProductoService;
import com.tiendadbii.tiendadbii.util.swagger_example.ProductoExample;
import com.tiendadbii.tiendadbii.views.ProductoViews;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/producto")
@Tag(name = "4. Producto", description = "Con este EndPoint se puede realizar todas las operaciones relacionadas a los productos.")
public class ProductoApi {
  private final IProductoService productoService;
  private final ModelMapper modelMapper;

  @Operation(summary = "Buscar un Producto dado {codigoProducto}", description = "Retorna un producto con todos sus campos.",
    responses = {
      @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Producto no encontrada con el codigoProducto: {codigoProducto}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
    })
  @JsonView({ProductoViews.Ver.class})
  @GetMapping("/{codigoProducto}")
  public ResponseEntity<ProductoDto> findById(@PathVariable Integer codigoProducto) {
    Producto producto = productoService.findById(codigoProducto);
    if (producto == null) {
      throw new EntityNotFoundException("Producto no encontrada con el codigoProducto: " + codigoProducto);
    }
    return ResponseEntity.ok().body(this.toDto(producto));
  }

  @Operation(summary = "Lista de todos los productos en formato de página", description = "La búsqueda se procesa basandose en 4 parámetros.")
  @GetMapping("/pageable")
  @JsonView({ProductoViews.Ver.class})
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

  @Operation(summary = "Actualizar Producto", description = "Actualiza todos los campos de un producto existente",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description= "Se podran actualizar todos los campos del Producto, a excepcion del campo \"stock\" y \"codigoBarra\"",
      content = @Content(examples = {
        @ExampleObject(
          summary = "Actualización simple de producto",
          value = ProductoExample.PRODUCTO_ACTUALIZAR_SIMPLE
        )
      })
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
        content = @Content(examples = {
          @ExampleObject(name = "Retorna todos los campos, incluyendo stock y codigoBarra", value = ProductoExample.PRODUCTO_VER, summary = "Producto actualizado")
        })),
      @ApiResponse(responseCode = "400", description = "Error en la Request. Debe asegurarse de completar todos los campos."),
      @ApiResponse(responseCode = "404", description = "Producto no encontrado con el codigoProducto: {codigoProducto}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
    })
  @PutMapping
  @JsonView({ProductoViews.Ver.class})
  public ResponseEntity<?> updateProducto(@RequestBody @JsonView({ProductoViews.Actualizar.class}) ProductoDto dto) {
    return ResponseEntity.ok().body(this.toDto(productoService.update(this.toEntity(dto))));
  }

  private ProductoDto toDto(Producto entity) {
    return modelMapper.map(entity, ProductoDto.class);
  }

  private Producto toEntity(ProductoDto dto) {
    return modelMapper.map(dto, Producto.class);
  }
}
