package com.tiendadbii.tiendadbii.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Producto}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto implements Serializable {
  private LocalDateTime fechaRegistro;
  private Integer codigoProducto;
  private String codigoBarra;
  private String nombreProducto;
  private String descripcion;
  private Float precioProducto;
  private Float precioVenta;
  private Integer stock;
  private String rutaArchivo;
}