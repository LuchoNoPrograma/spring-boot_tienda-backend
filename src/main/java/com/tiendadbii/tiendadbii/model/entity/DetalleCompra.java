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
@Table(name = "detalle_compra")
public class DetalleCompra extends AuditoriaRevision{
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "producto_codigo_producto")
  private Producto producto;


  @ManyToOne
  @JoinColumn(name = "compra_id_compra")
  private Compra compra;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idDetalleC;
  private LocalDateTime fechaDetalleC;
  private Integer cantidadDetalleC;
  private Double subtotalDetalleC;
}
