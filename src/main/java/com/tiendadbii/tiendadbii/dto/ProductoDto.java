package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.util.views.CompraViews;
import com.tiendadbii.tiendadbii.util.views.ProveedorViews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ProductoDto}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto implements Serializable {
  private LocalDateTime fechaRegistro;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Integer codigoProducto;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private String codigoBarra;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private String nombreProducto;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private String descripcion;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Float precioProducto;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Float precioVenta;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Integer stock;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private String rutaArchivo;
}