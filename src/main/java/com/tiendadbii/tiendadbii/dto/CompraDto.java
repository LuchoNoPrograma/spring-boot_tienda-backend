package com.tiendadbii.tiendadbii.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Compra}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDto implements Serializable {
  LocalDateTime fechaRegistro;
  Integer idCompra;
  LocalDateTime fechaCompra;
  Float totalCompra;
  Float totalDescCompra;
}