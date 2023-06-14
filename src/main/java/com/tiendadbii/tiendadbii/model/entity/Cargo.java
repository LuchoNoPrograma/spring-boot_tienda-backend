package com.tiendadbii.tiendadbii.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cargo")
public class Cargo extends AuditoriaRevision {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idCargo;
  private String nombreCargo;
  private String descripcionCargo;
}
