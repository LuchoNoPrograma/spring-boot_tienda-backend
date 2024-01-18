package com.tiendadbii.tiendadbii.dto;

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
  List<CompraDto> listaCompra;

  Integer idProveedor;
  String email;
}