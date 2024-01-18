package com.tiendadbii.tiendadbii.dto;

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
  ProveedorDto proveedor;
  List<DetalleCompraDto> listaDetalleCompra;

  LocalDateTime fechaRegistro;
  Integer idCompra;
  LocalDateTime fechaCompra;
  Float totalCompra;
  Float totalDescCompra;
}