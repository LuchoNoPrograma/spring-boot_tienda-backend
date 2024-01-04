package com.tiendadbii.tiendadbii.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.parent.Persona}
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDto implements Serializable {
  private LocalDateTime fechaRegistro;
  private String ci;
  private String nombres;
  private String apellidos;
  private String direccion;
  private String celular;
  private String prefijoCelular;
  @Getter(AccessLevel.NONE)
  private String nombreCompleto;

  public String getNombreCompleto() {
    return this.nombres + " " + this.apellidos;
  }
}