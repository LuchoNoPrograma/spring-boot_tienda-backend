package com.tiendadbii.tiendadbii.controller;

import com.tiendadbii.tiendadbii.dto.CargoDto;
import com.tiendadbii.tiendadbii.model.Estado;
import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/cargo")
@Tag(name = "Cargo", description = "API CRUD operations for Cargo")
public class CargoApi {
  private final ICargoService cargoService;
  private final ModelMapper modelMapper;

  @Operation(summary = "List all registered Cargo", description = "The search is performed in the database with restriction= estado != ELIMINADO")
  @GetMapping
  public ResponseEntity<List<CargoDto>> findAll() {
    List<Cargo> filteredList = cargoService.findAll().stream().filter(cargo -> cargo.getEstado() != Estado.ELIMINADO).toList();
    return ResponseEntity.ok().body(filteredList.stream().map(this::toDto).toList());
  }

  @Operation(summary = "Create new Cargo", description = "Given an Cargo Dto, it will be created in the database, id must be null")
  @PostMapping
  public ResponseEntity<CargoDto> createCargo(CargoDto dto) {
    return ResponseEntity.ok(this.toDto(cargoService.createNew(this.toEntity(dto))));
  }

  @Operation(summary = "Update Cargo", description = "Given a Cargo Dto, the Cargo will be updated in the database, id must not be null")
  @PutMapping
  public ResponseEntity<CargoDto> updateCargo(@RequestBody CargoDto dto) {
    return ResponseEntity.ok(this.toDto(cargoService.update(this.toEntity(dto))));
  }

  @Operation(summary = "Delete Cargo", description = "Given a Cargo Dto, the Cargo will be deleted and the response will be empty, id must not be null")
  @DeleteMapping("/{idCargo}")
  public ResponseEntity<CargoDto> deleteCargo(@PathVariable Integer idCargo) {
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
