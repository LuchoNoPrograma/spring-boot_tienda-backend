package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.enums.DiaEnum;
import com.tiendadbii.tiendadbii.views.EmpleadoViews;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
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
  @JsonView({EmpleadoViews.Ver.class})
  Integer idHorario;

  @JsonView({EmpleadoViews.Actualizar.class, EmpleadoViews.Crear.class, EmpleadoViews.Ver.class})
  DiaEnum dia;


  @Schema(type = "String", pattern = "HH:mm:SS")
  @JsonView({EmpleadoViews.Actualizar.class, EmpleadoViews.Crear.class, EmpleadoViews.Ver.class})
  LocalTime horaIngreso;

  @Schema(type = "String", pattern = "HH:mm:SS")
  @JsonView({EmpleadoViews.Actualizar.class, EmpleadoViews.Crear.class, EmpleadoViews.Ver.class})
  LocalTime horaSalida;
}