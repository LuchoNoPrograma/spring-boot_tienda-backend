package com.tiendadbii.tiendadbii.model.entity.parent;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public abstract class Persona extends AuditoriaRevision{
  @Column(name = "ci", length = 30, nullable = false)
  private String ci;

  @Column(name = "nombres", length = 40, nullable = false)
  private String nombres;

  @Column(name = "apellidos", length = 55, nullable = false)
  private String apellidos;

  @Column(name = "direccion", length = 55)
  private String direccion;

  @Column(name = "celular", length = 14, nullable = false)
  private String celular;

  @Column(name = "prefijo_celular", length = 6)
  private String prefijoCelular;
}
