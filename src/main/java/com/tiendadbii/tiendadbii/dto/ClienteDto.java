package com.tiendadbii.tiendadbii.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Cliente}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto extends PersonaDto implements Serializable {
  private Integer idCliente;

  @Email(message = "email must be valid")
  private String email;
}