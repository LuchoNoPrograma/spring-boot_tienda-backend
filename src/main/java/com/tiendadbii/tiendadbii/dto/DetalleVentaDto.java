package com.tiendadbii.tiendadbii.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

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
  private Integer codigoProducto;

  @NotNull(message = "fechaVenta cannot be null")
  private LocalDateTime fechaDetalle;

  @NotNull(message = "cantidad cannot be null")
  private Integer cantidad;

  /*@NotNull(message = "subtotalDetalle cannot be null")*/
  private Double subtotalDetalle;

  private Double subtotalDescDetalle;
}