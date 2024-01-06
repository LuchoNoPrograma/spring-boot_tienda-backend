package com.tiendadbii.tiendadbii.dto;

import com.tiendadbii.tiendadbii.model.entity.Cargo;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Ocupa}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OcupaDto implements Serializable {
  String nombreCargo;

  Integer idCargo;
  Cargo cargo;
  LocalDate fechaInicio;
  LocalDate fechaFin;
}