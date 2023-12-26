package com.tiendadbii.tiendadbii.dto;

import lombok.*;

import java.util.List;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Empleado}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDto extends PersonaDto {
  private Integer idEmpleado;
  private String email;
  private List<HorarioDto> listaHorario; //<-- Relationship between Empleado and Horario
  private List<OcupaDto> listaOcupa; //<-- Relationship n:n between Empleado and Cargo
}