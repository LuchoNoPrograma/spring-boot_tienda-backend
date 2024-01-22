package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.AuditoriaRevision;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
@Builder
public class Producto extends AuditoriaRevision {
  @OneToMany(mappedBy = "producto", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.DETACH})
  @ToString.Exclude
  private List<DetalleCompra> listaDetalleCompra;

  @OneToMany(mappedBy = "producto", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.DETACH})
  @ToString.Exclude
  private List<DetalleVenta> listaDetalleVenta;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "codigo_producto")
  private Integer codigoProducto;

  @Column(name = "codigo_barra", nullable = false, unique = true)
  private String codigoBarra;

  @Column(name = "nombre_producto", length = 90, nullable = false)
  private String nombreProducto;

  @Column(name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;

  @Column(name = "precio_producto", nullable = false)
  private Float precioProducto;

  @Column(name = "precio_venta")
  private Float precioVenta;

  @Column(name = "stock", nullable = false)
  private Integer stock;

  @Column(name = "ruta_archivo")
  private String rutaArchivo;
}
