package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.AuditoriaRevision;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ocupa")
public class Ocupa extends AuditoriaRevision {
  @ManyToOne
  @JoinColumn(name = "fk_id_cargo", foreignKey = @ForeignKey(name = "cargo_se_asigna_a_empleado"))
  private Cargo cargo;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
}
