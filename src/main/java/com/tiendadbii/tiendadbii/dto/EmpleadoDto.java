package com.tiendadbii.tiendadbii.dto;

import com.tiendadbii.tiendadbii.model.Estado;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Empleado}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDto implements Serializable {
  private Estado estado;
  private LocalDateTime fechaRegistro;
  private String ci;
  private String nombres;
  private String apellidos;
  private String direccion;
  private String celular;
  private String prefijoCelular;
  private Integer idEmpleado;
  private String email;
  private List<HorarioDto> listaHorario; //<-- Relationship between Empleado and Horario
  private List<OcupaDto> listaOcupa; //<-- Relationship n:n between Empleado and Cargo
}