package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.AuditoriaRevision;
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
  @Column(name = "id_cargo")
  private Integer idCargo;

  @Column(name = "nombre_cargo", length = 55, nullable = false)
  private String nombreCargo;

  @Column(name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;
}
