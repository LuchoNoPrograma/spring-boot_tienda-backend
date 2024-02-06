package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.views.VentaViews;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.DetalleVenta}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaDto implements Serializable {
  private Integer idDetalleVenta;

  @NotNull(message = "codigoProducto cannot be null")
  @JsonView({VentaViews.Crear.class})
  private Integer codigoProducto;

  @NotNull(message = "fechaVenta cannot be null")
  @JsonView({VentaViews.Crear.class})
  private LocalDateTime fechaDetalle;

  @NotNull(message = "cantidad cannot be null")
  @JsonView({VentaViews.Crear.class})
  private Integer cantidad;

  /*@NotNull(message = "subtotalDetalle cannot be null")*/
  @JsonView({VentaViews.Crear.class})
  private Double subtotalDetalle;

  @JsonView({VentaViews.Crear.class})
  private Double subtotalDescDetalle;
}