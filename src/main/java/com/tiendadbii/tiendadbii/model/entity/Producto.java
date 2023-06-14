package com.tiendadbii.tiendadbii.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto extends AuditoriaRevision{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer codigoProducto;
  private String codigoBarra;
  private String nombreProducto;
  private String descripcionProducto;
  private Double precioProducto;
  private Double precioVenta;
  private Integer stockProducto;
}
