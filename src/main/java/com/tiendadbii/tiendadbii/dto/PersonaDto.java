package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.util.views.PersonaViews;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.parent.Persona}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDto implements Serializable {
  @JsonView({PersonaViews.Privado.class})
  @Null(message = "fechaRegistro should be null")
  private LocalDateTime fechaRegistro;

  @JsonView({PersonaViews.Ver.class, PersonaViews.Actualizar.class, PersonaViews.Crear.class})
  @NotNull(message = "ci cannot be null")
  @Size(min = 4, max = 30, message = "ci must be between 4 and 16 characters")
  private String ci;

  @JsonView({PersonaViews.Ver.class, PersonaViews.Actualizar.class, PersonaViews.Crear.class})
  @NotNull(message = "nombres cannot be null")
  @Size(min = 2, max = 40, message = "nombres must be between 2 and 50 characters")
  private String nombres;

  @JsonView({PersonaViews.Ver.class, PersonaViews.Actualizar.class, PersonaViews.Crear.class})
  @NotNull(message = "apellidos cannot be null")
  @Size(min = 2, max = 55, message = "apellidos must be between 2 and 55 characters")
  private String apellidos;

  @JsonView({PersonaViews.Ver.class, PersonaViews.Actualizar.class, PersonaViews.Crear.class})
  @Size(max = 55, message = "direccion must be less than 55 characters")
  private String direccion;

  @JsonView({PersonaViews.Ver.class, PersonaViews.Actualizar.class, PersonaViews.Crear.class})
  @NotNull(message = "celular cannot be null")
  @Size(min = 6, max = 14, message = "celular must be between 6 and 14 characters")
  private String celular;

  @JsonView({PersonaViews.Ver.class, PersonaViews.Actualizar.class, PersonaViews.Crear.class})
  private String prefijoCelular;

  @JsonView(PersonaViews.Ver.class)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private String nombreCompleto;

  public String getNombreCompleto() {
    return this.nombres + " " + this.apellidos;
  }
}