package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.util.views.CompraViews;
import com.tiendadbii.tiendadbii.util.views.ProveedorViews;
import com.tiendadbii.tiendadbii.util.views.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Compra}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDto implements Serializable {
  @JsonView({CompraViews.ConProveedorId.class})
  ProveedorDto proveedor;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  List<DetalleCompraDto> listaDetalleCompra;

  @JsonView(Views.Private.class)
  LocalDateTime fechaRegistro;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  Integer idCompra;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  LocalDateTime fechaCompra;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  Float totalCompra;

  @JsonView({CompraViews.ConProveedorId.class, ProveedorViews.ConListaCompra.class})
  Float totalDescCompra;

}