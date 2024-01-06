package com.tiendadbii.tiendadbii.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
  @Schema(type = "String", pattern = "HH:mm:SS")
  LocalTime horaIngreso;

  @Schema(type = "String", pattern = "HH:mm:SS")
  LocalTime horaSalida;
}