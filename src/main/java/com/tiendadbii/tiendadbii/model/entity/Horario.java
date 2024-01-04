package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.AuditoriaRevision;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horario")
public class Horario extends AuditoriaRevision {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_id_empleado", foreignKey = @ForeignKey(name = "horario_pertenece_a_empleado"))
  private Empleado empleado;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_horario")
  private Integer idHorario;

  @Column(name = "dia", length = 155, nullable = false)
  private String dia;

  @Column(name = "hora_ingreso", nullable = false)
  private LocalTime horaIngreso;

  @Column(name = "hora_salida", nullable = false)
  private LocalTime horaSalida;

  @Override
  public String toString() {
    return "Horario{" +
      "empleado=" + empleado.getIdEmpleado() +
      ", idHorario=" + idHorario +
      ", dia='" + dia + '\'' +
      ", horaIngreso=" + horaIngreso +
      ", horaSalida=" + horaSalida +
      "} " + super.toString();
  }
}
