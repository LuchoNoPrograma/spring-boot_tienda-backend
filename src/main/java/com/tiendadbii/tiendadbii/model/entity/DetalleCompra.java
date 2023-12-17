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
@Table(name = "detalle_compra")
public class DetalleCompra extends AuditoriaRevision {
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_codigo_producto", foreignKey = @ForeignKey(name = "detalle_compra_tiene_producto"))
  private Producto producto;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_id_compra", foreignKey = @ForeignKey(name = "detalle_compra_pertenece_a_compra"))
  private Compra compra;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_detalle_compra")
  private Integer idDetalleCompra;

  @Column(name = "fecha_detalle", nullable = false)
  private LocalDateTime fechaDetalle;

  @Column(name = "cantidad", nullable = false)
  private Integer cantidad;

  @Column(name = "subtotal_detalle")
  private Float subtotalDetalle;

  @Column(name = "subtotal_desc_detalle")
  private Float subtotalDescDetalle;
}
