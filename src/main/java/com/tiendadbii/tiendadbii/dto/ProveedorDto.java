package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.util.views.CompraViews;
import com.tiendadbii.tiendadbii.util.views.ProveedorViews;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Proveedor}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDto extends PersonaDto implements Serializable {
  @JsonView({ProveedorViews.ConListaCompra.class})
  List<CompraDto> listaCompra;

  @JsonView({ProveedorViews.ConListaCompra.class, ProveedorViews.SinListaCompra.class, ProveedorViews.Actualizar.class,
    CompraViews.ConProveedorId.class, CompraViews.ConProveedor.class})
  Integer idProveedor;

  @JsonView({ProveedorViews.ConListaCompra.class, ProveedorViews.SinListaCompra.class, ProveedorViews.Actualizar.class,
    CompraViews.ConProveedor.class, ProveedorViews.Crear.class})
  String email;
}