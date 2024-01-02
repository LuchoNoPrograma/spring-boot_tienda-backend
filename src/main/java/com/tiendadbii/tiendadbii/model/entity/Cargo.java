package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.Estado;
import com.tiendadbii.tiendadbii.model.entity.parent.AuditoriaRevision;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
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

  @Builder
  public Cargo(Integer idCargo, String nombreCargo, String descripcion, LocalDateTime fechaRegistro, Estado estado) {
    this.idCargo = idCargo;
    this.nombreCargo = nombreCargo;
    this.descripcion = descripcion;
    this.setFechaRegistro(fechaRegistro);
    this.setEstado(estado);
  }
}
