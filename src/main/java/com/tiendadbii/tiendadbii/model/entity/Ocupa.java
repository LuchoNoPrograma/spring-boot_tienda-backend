package com.tiendadbii.tiendadbii.model.entity;

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
public class Ocupa extends AuditoriaRevision{
  @ManyToOne
  @JoinColumn(name = "cargo_id_cargo")
  private Cargo cargo;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "empleado_id_empleado")
  private Empleado empleado;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer codigoOcupa;
  private LocalDate fechaInicio;
  private LocalDate fechaFinal;
}
