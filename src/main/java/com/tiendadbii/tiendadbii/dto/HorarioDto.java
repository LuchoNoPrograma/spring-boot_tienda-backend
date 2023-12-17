package com.tiendadbii.tiendadbii.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Horario}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDto implements Serializable {
  String dia;
  LocalDateTime horaIngreso;
  LocalDateTime horaSalida;
}