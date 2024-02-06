package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.views.EmpleadoViews;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Ocupa}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OcupaDto implements Serializable {
  @JsonView({EmpleadoViews.Ver.class})
  private Integer codigoOcupa;

  @JsonView({EmpleadoViews.Actualizar.class ,EmpleadoViews.Crear.class, EmpleadoViews.Ver.class})
  private Integer idCargo;

  @JsonView({EmpleadoViews.Ver.class})
  private String nombreCargo;

  @JsonView({EmpleadoViews.Actualizar.class, EmpleadoViews.Crear.class, EmpleadoViews.Ver.class})
  private LocalDate fechaInicio;

  @JsonView({EmpleadoViews.Actualizar.class, EmpleadoViews.Crear.class, EmpleadoViews.Ver.class})
  private LocalDate fechaFin;
}