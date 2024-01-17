package com.tiendadbii.tiendadbii.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Proveedor}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDto implements Serializable {
  String ci;
  String nombres;
  String apellidos;
  String direccion;
  String celular;
  String prefijoCelular;
  List<CompraDto> listaCompra;
  Integer idProveedor;
  String email;
}