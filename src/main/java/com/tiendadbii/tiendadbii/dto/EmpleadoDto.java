package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.views.EmpleadoViews;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Empleado}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDto extends PersonaDto {
  @JsonView({EmpleadoViews.Actualizar.class, EmpleadoViews.Ver.class})
  private Integer idEmpleado;

  @JsonView({EmpleadoViews.Actualizar.class, EmpleadoViews.Crear.class, EmpleadoViews.Ver.class})
  private String email;

  @JsonView({EmpleadoViews.Actualizar.class, EmpleadoViews.Crear.class, EmpleadoViews.Ver.class})
  private List<HorarioDto> listaHorario; //<-- Relationship between Empleado and Horario

  @JsonView({EmpleadoViews.Actualizar.class, EmpleadoViews.Crear.class, EmpleadoViews.Ver.class})
  private List<OcupaDto> listaOcupa; //<-- Relationship n:n between Empleado and Cargo

  @Builder
  public EmpleadoDto(LocalDateTime fechaRegistro, String ci, String nombres, String apellidos, String direccion, String celular, String prefijoCelular, String nombreCompleto, Integer idEmpleado, String email, List<HorarioDto> listaHorario, List<OcupaDto> listaOcupa) {
    super(fechaRegistro, ci, nombres, apellidos, direccion, celular, prefijoCelular, nombreCompleto);
    this.idEmpleado = idEmpleado;
    this.email = email;
    this.listaHorario = listaHorario;
    this.listaOcupa = listaOcupa;
  }
}