package com.tiendadbii.tiendadbii.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.dto.DetalleVentaDto;
import com.tiendadbii.tiendadbii.dto.VentaDto;
import com.tiendadbii.tiendadbii.model.entity.DetalleVenta;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.entity.Producto;
import com.tiendadbii.tiendadbii.model.entity.Venta;
import com.tiendadbii.tiendadbii.model.service.interfaces.IEmpleadoService;
import com.tiendadbii.tiendadbii.model.service.interfaces.IProductoService;
import com.tiendadbii.tiendadbii.model.service.interfaces.IVentaService;
import com.tiendadbii.tiendadbii.util.swagger_example.VentaExample;
import com.tiendadbii.tiendadbii.views.VentaViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/venta")
@Validated
@Tag(name = "3. Venta", description = "Con este EndPoint se puede realizar todas las operaciones de Venta.")
@Log4j2
public class VentaApi {
  private final IVentaService ventaService;
  private final IEmpleadoService empleadoService;
  private final ModelMapper modelMapper;
  private final IProductoService productoService;

  @Operation(summary = "Buscar una Venta dado {nroVenta}", description = "Retornar una venta desde la base de datos",
    responses = {
      @ApiResponse(responseCode = "200", description = "Venta encontrado exitosamente",
        content = {/*@Content(schema = @Schema(implementation = CompraDto.class))*/}),
      @ApiResponse(responseCode = "404", description = "Venta no encontrada con el nroVenta : {nroVenta}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
      @ApiResponse(responseCode = "500", description = "Error de servidor", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
    })
  @JsonView({VentaViews.ConListaDetalleVenta.class})
  @GetMapping("/{nroVenta}")
  public ResponseEntity<VentaDto> findById(@PathVariable Integer nroVenta) {
    Venta venta = ventaService.findById(nroVenta);
    if (venta == null) {
      throw new EntityNotFoundException("Venta no encontrada con el nroVenta: " + nroVenta);
    }

    //Estrategia estricta, ambiguedad con producto.codigoProducto, producto.codigoBarra, al hacer esto codigoProducto sera nulo
    //Se tiene que establecer la estrategia estricta y desactivar la ambiguedad con los atributos
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setAmbiguityIgnored(true);
    //Tipeado para poder establecer codigoProducto de producto.codigoProducto
    modelMapper.createTypeMap(DetalleVenta.class, DetalleVentaDto.class).addMapping(srcEntity -> srcEntity.getProducto().getCodigoProducto(), DetalleVentaDto::setCodigoProducto);
    return ResponseEntity.ok().body(this.toDto(venta));
  }

  @Operation(summary = "Lista todas las ventas", description = "La búsqueda retorna una lista de ventas con todos sus campos a excepcion del campo \"listaDetalleVenta\".",
    responses = {
      @ApiResponse(responseCode = "200", description = "Lista de ventas encontrada exitosamente"),
    })
  @JsonView({VentaViews.SinListaDetalleVenta.class})
  @GetMapping
  public ResponseEntity<List<VentaDto>> findAll() {
    //Estrategia estricta, ambiguedad con producto.codigoProducto, producto.codigoBarra
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setAmbiguityIgnored(true);

    return ResponseEntity.ok().body(ventaService.findAll().stream().map(this::toDto).toList());
  }

  @Operation(
    summary = "Registrar venta (Crear nueva venta)",
    description = """
      Para realizar el registro de una venta debe proporcionar todos los campos establecidos en el ejemplo.
      
      El campo "listaDetalleVenta" establece todos los datos de los producto a vender, se tiene que considerar lo siguiente:\n
       1) El campo "cantidad" reducira el stock del producto asociado.\n
       2) El campo "subtotalDetalle" del Response es calculado basandose en el "precioVenta" del producto multiplicado por la cantidad.\n
       3) El campo "totalVenta" del response es calculado basandose en la sumatoria de "subtotalDetalle" del Response. 
      """,
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "Se debe establecer el {idEmpleado} en el EndPoint",
      content = @Content(examples = {
        @ExampleObject(
          summary = "Registrar venta simple",
          name = "Registrar venta simple",
          value = VentaExample.VENTA_PARA_REGISTRAR,
          description = """
            Ejemplo del cuerpo de la petición para registrar una venta. \n
            """
        )
      })
    ),
    responses = {
      @ApiResponse(responseCode = "201", description = "Venta created succesfully, returned Venta",
        content = {@Content(schema = @Schema(implementation = VentaDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad request, invalid field"),
      @ApiResponse(responseCode = "404", description = "Not found, resource not found")
    })
  @PostMapping("/empleado/{idEmpleado}")
  public ResponseEntity<?> createVenta(@RequestBody @Valid VentaDto dto, @PathVariable Integer idEmpleado) {
    Empleado empleado = empleadoService.findById(idEmpleado);
    if (empleado == null)
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado with idEmpleado: " + idEmpleado + " not found");
    for (DetalleVentaDto venta : dto.getListaDetalleVenta()) {
      if (venta.getCodigoProducto() == null)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Each DetalleVenta must have a codigoProducto");
    }


    Venta venta = modelMapper.map(dto, Venta.class);
    venta.setEmpleado(empleado);

    for (DetalleVentaDto detalleVentaDto : dto.getListaDetalleVenta()) {
      DetalleVenta detalleVenta = modelMapper.map(detalleVentaDto, DetalleVenta.class);

      Producto producto = productoService.findById(detalleVentaDto.getCodigoProducto());
      if (producto == null)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto with codigoProducto: " + detalleVentaDto.getCodigoProducto() + " not found");

      detalleVenta.setProducto(producto);
    }

    Venta ventaPersisted = ventaService.createNew(venta);

    modelMapper.getConfiguration().setAmbiguityIgnored(true);
    modelMapper.createTypeMap(DetalleVenta.class, DetalleVentaDto.class).addMapping(srcEntity -> srcEntity.getProducto().getCodigoProducto(), DetalleVentaDto::setCodigoProducto);
    VentaDto ventaDto = this.toDto(ventaPersisted);


    return ResponseEntity
      .status(HttpStatus.CREATED)
      .header("Location", "/api/venta/" + ventaDto.getNroVenta())
      .body(ventaDto);
  }

  private Venta toEntity(VentaDto dto) {
    Venta venta = modelMapper.map(dto, Venta.class);
    if (dto.getListaDetalleVenta() != null) {
      List<DetalleVenta> listaDetalleVenta = dto.getListaDetalleVenta().stream().map(detalleVentaDto -> {
        DetalleVenta detalleVenta = modelMapper.map(detalleVentaDto, DetalleVenta.class);
        detalleVenta.setProducto(productoService.findById(detalleVentaDto.getCodigoProducto()));

        return detalleVenta;
      }).toList();
      venta.setListaDetalleVenta(listaDetalleVenta);
    }

    return venta;
  }

  private VentaDto toDto(Venta entity) {
    VentaDto ventaDto = modelMapper.map(entity, VentaDto.class);
    if (entity.getListaDetalleVenta() != null) {
      List<DetalleVentaDto> listaDetalleVentaDto = entity.getListaDetalleVenta().stream().map(detalleVenta -> {
        detalleVenta.setVenta(null);
        return modelMapper.map(detalleVenta, DetalleVentaDto.class);
      }).toList();
      ventaDto.setListaDetalleVenta(listaDetalleVentaDto);
    }
    return ventaDto;
  }
}
