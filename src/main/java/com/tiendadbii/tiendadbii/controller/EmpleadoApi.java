package com.tiendadbii.tiendadbii.controller;

import com.tiendadbii.tiendadbii.model.Estado;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/empleado")
public class EmpleadoApi {
  public final EmpleadoRepository empleadoRepository;

  @GetMapping
  public ResponseEntity<?> getAllEmpleados(){
    return ResponseEntity.ok(empleadoRepository.findAllByEstado(Estado.ACTIVO));
  }

  @GetMapping("/pageable")
  public ResponseEntity<?> listarPaginados(
          @RequestParam(required = false, defaultValue = "0") int page,
          @RequestParam(required = false, defaultValue = "10") int size,
          @RequestParam(required = false, defaultValue = "idPersona") String sort,
          @RequestParam(required = false, defaultValue = "ASC") String order) {
    try {
      Sort.Direction direction = Sort.Direction.fromString(order);
      Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

      return ResponseEntity.ok().body(empleadoRepository.findAll(pageable).get().toList());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @GetMapping("/{idEmpleado}")
  public ResponseEntity<?> getEmpleadoById(@PathVariable Integer idEmpleado){
    return ResponseEntity.ok().body(empleadoRepository.findById(idEmpleado).orElse(null));
  }

  @PostMapping
  public ResponseEntity<?> createEmpleado(@RequestBody Empleado empleado){
    return ResponseEntity.ok().body(empleadoRepository.save(empleado));
  }

  @PutMapping
  public ResponseEntity<?> updateEmpleado(@RequestBody Empleado empleado){
    return ResponseEntity.ok().body(empleadoRepository.save(empleado));
  }

  @DeleteMapping("/{idEmpleado}")
  public ResponseEntity<?> deleteEmpleado(@PathVariable Integer idEmpleado){
    Empleado empleado = empleadoRepository.findById(idEmpleado).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    empleado.setEstado(Estado.INACTIVO);
    return ResponseEntity.ok().build();
  }
}
