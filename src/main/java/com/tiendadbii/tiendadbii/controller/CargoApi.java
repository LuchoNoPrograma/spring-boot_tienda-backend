package com.tiendadbii.tiendadbii.controller;

import com.tiendadbii.tiendadbii.dto.CargoDto;
import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/cargo")
@Tag(name = "Cargo", description = "API CRUD operations for Cargo")
@Log4j2
public class CargoApi {
  private final ICargoService cargoService;
  private final ModelMapper modelMapper;

  @Operation(summary = "List all registered Cargo", description = "The search is performed in the database without filter")
  @GetMapping
  public ResponseEntity<List<CargoDto>> findAll() {
    return ResponseEntity.ok(cargoService.findAll().stream().map(this::toDto).toList());
  }

  @Operation(summary = "Find Cargo with given ID", description = "Given an idCargo, it will return Cargo from DB")
  @GetMapping("/{idCargo}")
  public ResponseEntity<CargoDto> findById(@PathVariable Integer idCargo) {
    return ResponseEntity.ok(this.toDto(cargoService.findById(idCargo)));
  }

  @Operation(summary = "Create new Cargo", description = "Given an Cargo Dto, it will be created in the database, id must be null")
  @PostMapping
  public ResponseEntity<CargoDto> createCargo(@RequestBody CargoDto dto) {
    CargoDto cargoDto = this.toDto(cargoService.createNew(this.toEntity(dto)));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .header("Location", "/api/cargo/" + cargoDto.getIdCargo())
      .body(cargoDto);
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
