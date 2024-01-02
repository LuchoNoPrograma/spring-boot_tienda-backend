package com.tiendadbii.tiendadbii.controller;

import com.tiendadbii.tiendadbii.dto.EmpleadoDto;
import com.tiendadbii.tiendadbii.model.Estado;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.repository.EmpleadoRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.IEmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin({"*"})
@RequestMapping("/api/empleado")
@Tag(name = "Empleado", description = "Con esta API, puedes realizar operaciones como crear, leer, actualizar y eliminar registros de empleado")
public class EmpleadoApi {
  private final EmpleadoRepository empleadoRepository;
  private final ModelMapper modelMapper;
  private final IEmpleadoService empleadoService;

  @Operation(summary = "Listar todos los empleados registrados", description = "La busqueda se realiza en la base de datos sin ninguna restricción")
  /*@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpleadoDto.class)))),
  })*/
  @GetMapping
  public ResponseEntity<List<EmpleadoDto>> getAllEmpleados() {
    List<EmpleadoDto> listaEmpleadoDto = empleadoRepository.findAll(Sort.by("idEmpleado").descending()).stream().map(this::toDto).toList();
    return ResponseEntity.ok().body(listaEmpleadoDto);
  }

  @GetMapping("/pageable")
  public ResponseEntity<List<EmpleadoDto>> listarPaginados(
    @RequestParam(required = false, defaultValue = "0") int page,
    @RequestParam(required = false, defaultValue = "10") int size,
    @RequestParam(required = false, defaultValue = "idEmpleado") String sort,
    @RequestParam(required = false, defaultValue = "ASC") String order) {

    Sort.Direction direction = Sort.Direction.fromString(order);
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

    return ResponseEntity.ok().body(empleadoRepository.findAll(pageable).get().toList().stream().map(this::toDto).toList());
  }

  @GetMapping("/{idEmpleado}")
  public ResponseEntity<EmpleadoDto> getEmpleadoById(@PathVariable Integer idEmpleado) {
    return ResponseEntity.ok().body(toDto(empleadoRepository.findById(idEmpleado).orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con el id: " + idEmpleado))));
  }

  @Operation(summary = "Registrar un nuevo empleado", description = "Este registro requiere de cargos, que se envian mediante una entidad intermedia llamado Ocupa")
  @PostMapping
  public ResponseEntity<?> createEmpleado(@RequestBody EmpleadoDto empleadoDto) {
    Empleado empleado = this.toEntity(empleadoDto);
    return ResponseEntity.ok(empleadoService.createNew(empleado));
  }

  @PutMapping
  public ResponseEntity<?> updateEmpleado(@RequestBody Empleado empleado) {
    return ResponseEntity.ok().body(empleadoRepository.save(empleado));
  }

  @DeleteMapping("/{idEmpleado}")
  public ResponseEntity<?> deleteEmpleado(@PathVariable Integer idEmpleado) {
    Empleado empleado = empleadoRepository.findById(idEmpleado).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    empleado.setEstado(Estado.ELIMINADO);
    return ResponseEntity.ok().build();
  }

  private Empleado toEntity(EmpleadoDto dto) {
    return modelMapper.map(dto, Empleado.class);
  }

  private EmpleadoDto toDto(Empleado entity) {
    return modelMapper.map(entity, EmpleadoDto.class);
  }
}
