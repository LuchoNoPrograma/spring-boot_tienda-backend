package com.tiendadbii.tiendadbii.controller;

import com.tiendadbii.tiendadbii.dto.EmpleadoDto;
import com.tiendadbii.tiendadbii.dto.HorarioDto;
import com.tiendadbii.tiendadbii.dto.OcupaDto;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.entity.Horario;
import com.tiendadbii.tiendadbii.model.entity.Ocupa;
import com.tiendadbii.tiendadbii.model.service.interfaces.IEmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequiredArgsConstructor
@CrossOrigin({"*"})
@RequestMapping("/api/empleado")
@Tag(name = "Empleado", description = "With this API, you can perform operations such as creating, reading, updating, and deleting Empleado records.")
public class EmpleadoApi {
  private final ModelMapper modelMapper;
  private final IEmpleadoService empleadoService;

  @Operation(summary = "List all registered Empleado", description = "The search is performed in the database without any restrictions.")
  /*@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpleadoDto.class)))),
  })*/
  @GetMapping
  public ResponseEntity<List<EmpleadoDto>> findAll() {
    return ResponseEntity.ok().body(empleadoService.findAll().stream().map(this::toDto).toList());
  }

  @Operation(summary = "List all registered Empleado with pageable", description = "The search is performed in the database without any restrictions. based on 4 params, page, size, sort, order")
  @GetMapping("/pageable")
  public ResponseEntity<List<EmpleadoDto>> findAllPageable(
    @RequestParam(required = false, defaultValue = "0") int page,
    @RequestParam(required = false, defaultValue = "10") int size,
    @RequestParam(required = false, defaultValue = "idEmpleado") String sort,
    @RequestParam(required = false, defaultValue = "ASC") String order) {

    Sort.Direction direction = Sort.Direction.fromString(order);
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

    return ResponseEntity.ok().body(empleadoService.findAll(pageable).stream().map(this::toDto).toList());
  }

  @GetMapping("/{idEmpleado}")
  public ResponseEntity<EmpleadoDto> findById(@PathVariable Integer idEmpleado) {
    return ResponseEntity.ok().body(this.toDto(empleadoService.findById(idEmpleado)));
  }

  @Operation(summary = "Create new Empleado", description = "To create new Empleado, must have all fields, included a List of Ocupa and a List of Horario")
  @PostMapping
  public ResponseEntity<EmpleadoDto> createEmpleado(@RequestBody EmpleadoDto dto) {
    Empleado empleado = this.toEntity(dto);
    empleado.getListaHorario().forEach(horario -> horario.setEmpleado(empleado));
    empleado.getListaOcupa().forEach(ocupa -> ocupa.setEmpleado(empleado));

    EmpleadoDto empleadoDto = this.toDto(empleadoService.createNew(empleado));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .header("Location", "/api/empleado/" + empleadoDto.getIdEmpleado())
      .body(empleadoDto);
  }

  @Operation(summary = "Update Empleado", description = "To update Empleado, must have all fields, included a List of Ocupa and a List of Horario, old listaHorario and old listaOcupa will be overrided and deleted")
  @PutMapping
  public ResponseEntity<EmpleadoDto> updateEmpleado(@RequestBody Empleado empleado) {
    return ResponseEntity.ok(this.toDto(empleadoService.update(empleado)));
  }

  @Operation(summary = "Delete Empleado", description = "Will delete all Empleado data included listaOcupa and listaHorario")
  @DeleteMapping("/{idEmpleado}")
  public ResponseEntity<?> deleteEmpleado(@PathVariable Integer idEmpleado) {
    empleadoService.deleteById(idEmpleado);
    return ResponseEntity.noContent().build();
  }

  private Empleado toEntity(EmpleadoDto dto) {
    Empleado empleado = modelMapper.map(dto, Empleado.class);
    if (dto.getListaHorario() != null) {
      empleado.setListaHorario(dto.getListaHorario().stream().map(horario -> modelMapper.map(horario, Horario.class)).toList());
    }
    if (dto.getListaOcupa() != null) {
      empleado.setListaOcupa(dto.getListaOcupa().stream().map(ocupa -> modelMapper.map(ocupa, Ocupa.class)).toList());
    }

    return empleado;
  }

  private EmpleadoDto toDto(Empleado entity) {
    EmpleadoDto empleadoDto = modelMapper.map(entity, EmpleadoDto.class);
    if (entity.getListaHorario() != null) {
      empleadoDto.setListaHorario(entity.getListaHorario().stream().map(horario -> modelMapper.map(horario, HorarioDto.class)).toList());
    }
    if (entity.getListaOcupa() != null) {
      empleadoDto.setListaOcupa(entity.getListaOcupa().stream().map(ocupa -> modelMapper.map(ocupa, OcupaDto.class)).toList());
    }
    return empleadoDto;
  }
}
