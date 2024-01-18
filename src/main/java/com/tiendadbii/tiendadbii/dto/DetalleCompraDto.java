package com.tiendadbii.tiendadbii.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link DetalleCompra}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompraDto implements Serializable {
  private ProductoDto producto;

  private Integer idDetalleCompra;
  private LocalDateTime fechaDetalle;
  private Integer cantidad;
  private Float subtotalDetalle;
  private Float subtotalDescDetalle;
}