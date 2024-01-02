package com.tiendadbii.tiendadbii.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Cargo}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CargoDto implements Serializable {
  LocalDateTime fechaRegistro;
  Integer idCargo;
  String nombreCargo;
  String descripcion;
}