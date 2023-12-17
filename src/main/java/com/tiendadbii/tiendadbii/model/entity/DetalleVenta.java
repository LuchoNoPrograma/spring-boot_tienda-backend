package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.AuditoriaRevision;
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
public class DetalleVenta extends AuditoriaRevision {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_nro_venta", foreignKey = @ForeignKey(name = "detalle_venta_pertenece_a_venta"))
  private Venta venta;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_codigo_producto", foreignKey = @ForeignKey(name = "detalle_venta_contiene_producto"))
  private Producto producto;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_detalle_venta")
  private Integer idDetalleVenta;

  @Column(name = "fecha_detalle", nullable = false)
  private LocalDateTime fechaDetalle;

  @Column(name = "cantidad", nullable = false)
  private Integer cantidad;

  @Column(name = "subtotal_detalle")
  private Double subtotalDetalle;

@Column(name = "subtotal_desc_detalle")
  private Double subtotalDescDetalle;
}
