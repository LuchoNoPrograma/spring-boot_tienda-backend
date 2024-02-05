package com.tiendadbii.tiendadbii.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.dto.CompraDto;
import com.tiendadbii.tiendadbii.dto.ProveedorDto;
import com.tiendadbii.tiendadbii.model.entity.Compra;
import com.tiendadbii.tiendadbii.model.entity.Proveedor;
import com.tiendadbii.tiendadbii.model.service.interfaces.IProveedorService;
import com.tiendadbii.tiendadbii.util.views.ProveedorViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

@RestController
@RequiredArgsConstructor
@CrossOrigin({"*"})
@RequestMapping("/api/proveedor")
@Tag(name = "1. Proveedor", description = "Con este EndPoint puedes realizar operaciónes CRUD de Proveedor. " +
  "La entidad proveedor es necesaria para poder realizar las compras de productos")
public class ProveedorApi {
  private final ModelMapper modelMapper;
  private final IProveedorService proveedorService;

  @Operation(summary = "Lista todos los proveedores registrados", description = "La búsqueda se ejecuta en la base de datos sin ningun filtro. " +
    "Retorna todos los campos, excepto la lista de compras asociadas al proveedor.",
    responses = {@ApiResponse(responseCode = "200", description = "Lista de proveedores registrados")})
  @JsonView(ProveedorViews.SinListaCompra.class)
  @GetMapping
  public ResponseEntity<List<ProveedorDto>> findAll() {
    return ResponseEntity.ok().body(proveedorService.findAll().stream().map(this::toDto).toList());
  }

  @Operation(summary = "Lista todos los proveedores en formato de página", description = "La búsqueda es procesada en la base de datos, basandose en 4 parametros. " +
    "Retorna todos los campos, excepto la lista de compras asociadas al proveedor.",
    responses = {@ApiResponse(responseCode = "200", description = "Lista de proveedor registrados en formato de página")})
  @JsonView(ProveedorViews.SinListaCompra.class)
  @GetMapping("/pageable")
  public ResponseEntity<List<ProveedorDto>> findAllPageable(
    @RequestParam(required = false, defaultValue = "0") int page,
    @RequestParam(required = false, defaultValue = "10") int size,
    @RequestParam(required = false, defaultValue = "idProveedor") String sort,
    @RequestParam(required = false, defaultValue = "ASC") String order) {

    Sort.Direction direction = Sort.Direction.fromString(order);
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

    return ResponseEntity.ok().body(proveedorService.findAll(pageable).stream().map(this::toDto).toList());
  }

  @Operation(summary = "Buscar un Proveedor dado un {idProveedor}", description = "La búsqueda es procesada y retorna todos los datos del proveedor, incluyendo las compras asociadas al proveedor.",
    responses = {
      @ApiResponse(responseCode = "200", description = "Proveedor encontrado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Proveedor no encontrado con el id: {idProveedor}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
    })
  @JsonView(ProveedorViews.ConListaCompra.class)
  @GetMapping("/{idProveedor}")
  public ResponseEntity<ProveedorDto> findById(@PathVariable Integer idProveedor) {
    Proveedor proveedor = proveedorService.findById(idProveedor);
    if (proveedor == null) {
      throw new EntityNotFoundException("Proveedor no encontrado con el id: " + idProveedor);
    }
    return ResponseEntity.ok().body(this.toDto(proveedor));
  }

  @Operation(summary = "Crear nuevo Proveedor", description = "Para crear proveedor debe proporcionar todos los campos presentes en el ejemplo",
    responses = {
      @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Proveedor no encontrado con el id: {idProveedor}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor, mas detalles en campo \"message\"", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
    })
  @PostMapping
  @JsonView(ProveedorViews.Crear.class)
  public ResponseEntity<ProveedorDto> create(@RequestBody ProveedorDto dto) {
    Proveedor proveedor = modelMapper.map(dto, Proveedor.class);
    ProveedorDto proveedorDto = toDto(proveedorService.createNew(proveedor));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .header("Location", "/api/proveedor/" + proveedorDto.getIdProveedor())
      .body(proveedorDto);
  }

  @Operation(summary = "Actualizar proveedor",
    description = "Debe proporcionar el campo idProveedor, todos los campos de proveedor son actualizables excepto \"listaCompra\"",
    responses = {
      @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Proveedor no encontrado con el id: {idProveedor}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor, mas detalles en campo \"message\"", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
    }
  )
  @PutMapping
  @JsonView(ProveedorViews.Actualizar.class)
  public ResponseEntity<ProveedorDto> update(@RequestBody ProveedorDto proveedorDto) {
    if (proveedorService.findById(proveedorDto.getIdProveedor()) == null) {
      throw new EntityNotFoundException("Proveedor no encontrado con el id: " + proveedorDto.getIdProveedor());
    }
    return ResponseEntity.ok(toDto((proveedorService.update(toEntity(proveedorDto)))));
  }

  @Operation(summary = "Eliminar Proveedor", description = "Eliminara todo el registro del proveedor, incluyendo la lista de compras asociadas",
    responses = {
      @ApiResponse(responseCode = "204", description = "Proveedor eliminado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Proveedor no encontrado con el id: {idProveedor}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor, mas detalles en campo \"message\"", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
    }
  )
  @DeleteMapping("/{idProveedor}")
  public ResponseEntity<Void> deleteById(@PathVariable Integer idProveedor) {
    if (proveedorService.findById(idProveedor) == null) {
      throw new EntityNotFoundException("Proveedor no encontrado con el id: " + idProveedor);
    }
    proveedorService.deleteById(idProveedor);
    return ResponseEntity.noContent().build();
  }

  private Proveedor toEntity(ProveedorDto dto) {
    Proveedor proveedor = modelMapper.map(dto, Proveedor.class);
    if (dto.getListaCompra() != null) {
      proveedor.setListaCompra(dto.getListaCompra().stream().map(compra -> modelMapper.map(compra, Compra.class)).toList());
    }

    return proveedor;
  }

  private ProveedorDto toDto(Proveedor entity) {
    ProveedorDto proveedorDto = modelMapper.map(entity, ProveedorDto.class);
    entity.setListaCompra(null);
    if (entity.getListaCompra() != null) {
      proveedorDto.setListaCompra(entity.getListaCompra().stream().map(compra -> modelMapper.map(compra, CompraDto.class)).toList());
    }
    return proveedorDto;
  }
}
