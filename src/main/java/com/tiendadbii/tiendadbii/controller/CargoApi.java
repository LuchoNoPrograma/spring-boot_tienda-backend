package com.tiendadbii.tiendadbii.controller;

import com.tiendadbii.tiendadbii.dto.CargoDto;
import com.tiendadbii.tiendadbii.dto.ErrorDto;
import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/cargo")
@Tag(name = "6. Cargo", description = "Con este EndPoint se puede realizar todas las operaciones de Cargo.")
@Log4j2
public class CargoApi {
  private final ICargoService cargoService;
  private final ModelMapper modelMapper;

  @Operation(summary = "Listar todos los cargos", description = "Retorna la lista de todos los cargos sin aplicar filtro.")
  @GetMapping
  public ResponseEntity<List<CargoDto>> findAll() {
    return ResponseEntity.ok(cargoService.findAll().stream().map(this::toDto).toList());
  }

  @Operation(summary = "Buscar un Cargo dado {idCargo}", description = "Retorna un Cargo con todos sus campos.",
    responses = {
      @ApiResponse(responseCode = "200", description = "Cargo encontrado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Cargo no encontrado con el id: {idCargo}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
    }
  )
  @GetMapping("/{idCargo}")
  public ResponseEntity<CargoDto> findById(@PathVariable Integer idCargo) {
    Cargo cargo = cargoService.findById(idCargo);
    if (cargo == null) {
      throw new EntityNotFoundException("Cargo no encontrado con el id: " + idCargo);
    }
    return ResponseEntity.ok(this.toDto(cargo));
  }

  @Operation(summary = "Crear nuevo cargo", description = "Para crear un cargo debe proporcionar un valor a todos los campos a excepcion de " +
    "<b>\"fechaRegistro\"</b> e <b>\"idCargo\"</b>, las cuales se les asignara un valor internamente de manera automática.",
    responses = {
      @ApiResponse(responseCode = "201", description = "Cargo creado exitosamente"),
      @ApiResponse(responseCode = "400", description = "Error en la Request, asegurese de completar todos los campos de manera correcta", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor, mas detalles en campo \"message\"", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    }
  )
  @PostMapping
  public ResponseEntity<CargoDto> createCargo(@RequestBody CargoDto dto) {
    CargoDto cargoDto = this.toDto(cargoService.createNew(this.toEntity(dto)));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .header("Location", "/api/cargo/" + cargoDto.getIdCargo())
      .body(cargoDto);
  }

  @Operation(summary = "Actualizar cargo", description = "Todos los valores de un cargo pueden ser actualizados, a excepción del campo " +
    "<b>\"fechaRegistro\"</b>",
    responses = {
      @ApiResponse(responseCode = "200", description = "Cargo actualizado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Cargo no encontrado con el id: {idCargo}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor, mas detalles en campo \"message\"", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    }
  )
  @PutMapping
  public ResponseEntity<CargoDto> updateCargo(@RequestBody CargoDto dto) {
    Cargo cargo = cargoService.findById(dto.getIdCargo());
    if (cargo == null) {
      throw new EntityNotFoundException("Cargo no encontrado con el id: " + dto.getIdCargo());
    }
    return ResponseEntity.ok(this.toDto(cargoService.update(this.toEntity(dto))));
  }

  @Operation(summary = "Eliminar Cargo", description = "Eliminara el registro del cargo, sin embargo, para realizar la operación, el cargo no debe estar asociado a ningún empleado",
    responses = {
      @ApiResponse(responseCode = "204", description = "Cargo eliminado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Cargo no encontrado con el id: {idCargo}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor, mas detalles en campo \"message\"", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    }
  )
  @DeleteMapping("/{idCargo}")
  public ResponseEntity<Void> deleteCargo(@PathVariable Integer idCargo) {
    Cargo cargo = cargoService.findById(idCargo);
    if (cargo == null) {
      throw new EntityNotFoundException("Cargo no encontrado con el id: " + idCargo);
    }
    cargoService.deleteById(idCargo);
    return ResponseEntity.noContent().build();
  }

  private Cargo toEntity(CargoDto dto) {
    return modelMapper.map(dto, Cargo.class);
  }

  private CargoDto toDto(Cargo entity) {
    return modelMapper.map(entity, CargoDto.class);
  }
}
