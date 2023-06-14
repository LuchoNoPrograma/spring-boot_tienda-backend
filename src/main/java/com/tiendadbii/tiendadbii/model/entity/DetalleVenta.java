package com.tiendadbii.tiendadbii.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_venta")
public class DetalleVenta extends AuditoriaRevision{
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "venta_id_venta")
  private Venta venta;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "producto_codigo_producto")
  private Producto producto;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idDetalleV;
  private LocalDateTime fechDetV;
  private Double cantidadDetV;
  private Double subtotalDetV;
  private Double subtotalDescDetV;
}
