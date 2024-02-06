package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.views.CompraViews;
import com.tiendadbii.tiendadbii.views.ProductoViews;
import com.tiendadbii.tiendadbii.views.ProveedorViews;
import com.tiendadbii.tiendadbii.views.Views;
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
  @JsonView(Views.Private.class)
  private LocalDateTime fechaRegistro;

  @JsonView({ProductoViews.Ver.class, ProductoViews.Actualizar.class,
    CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Integer codigoProducto;

  @JsonView({ProductoViews.Ver.class, CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private String codigoBarra;

  @JsonView({ProductoViews.Ver.class, ProductoViews.Actualizar.class,
    CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private String nombreProducto;

  @JsonView({ProductoViews.Ver.class, ProductoViews.Actualizar.class,
    CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private String descripcion;

  @JsonView({ProductoViews.Ver.class, ProductoViews.Actualizar.class,
    CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Float precioProducto;

  @JsonView({ProductoViews.Ver.class, ProductoViews.Actualizar.class,
    CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Float precioVenta;

  @JsonView({ProductoViews.Ver.class, CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private Integer stock;

  @JsonView({ProductoViews.Ver.class, ProductoViews.Actualizar.class,
    CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  private String rutaArchivo;
}