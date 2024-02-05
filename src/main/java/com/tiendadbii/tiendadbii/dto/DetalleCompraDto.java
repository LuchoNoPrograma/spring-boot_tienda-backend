package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.views.CompraViews;
import com.tiendadbii.tiendadbii.views.ProveedorViews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.DetalleCompra}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompraDto implements Serializable {
  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private ProductoDto producto;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Integer idDetalleCompra;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private LocalDateTime fechaDetalle;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Integer cantidad;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Float subtotalDetalle;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Float subtotalDescDetalle;
}