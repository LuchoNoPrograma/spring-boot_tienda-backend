package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.enums.DiaEnum;
import com.tiendadbii.tiendadbii.model.entity.parent.AuditoriaRevision;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "horario")
public class Horario extends AuditoriaRevision {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_id_empleado", foreignKey = @ForeignKey(name = "horario_pertenece_a_empleado"))
  private Empleado empleado;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_horario")
  private Integer idHorario;

  @Enumerated(EnumType.STRING)
  @Column(name = "dia", nullable = false)
  private DiaEnum dia;

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
