package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.AuditoriaRevision;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ocupa")
public class Ocupa extends AuditoriaRevision {
  @ManyToOne
  @JoinColumn(name = "fk_id_cargo", foreignKey = @ForeignKey(name = "cargo_se_asigna_a_empleado"))
  private Cargo cargo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_id_empleado", foreignKey = @ForeignKey(name = "empleado_ocupa_cargo"))
  private Empleado empleado;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "codigo_ocupa")
  private Integer codigoOcupa;

  @Column(name = "fecha_inicio", nullable = false)
  private LocalDate fechaInicio;

  @Column(name = "fecha_fin")
  private LocalDate fechaFin;

  @Override
  public String toString() {
    return "Ocupa{" +
      "cargo=" + cargo.getIdCargo() + " - " + cargo.getNombreCargo() +
      ", empleado=" + empleado.getIdEmpleado() +
      ", codigoOcupa=" + codigoOcupa +
      ", fechaInicio=" + fechaInicio +
      ", fechaFin=" + fechaFin +
      "} " + super.toString();
  }
}
