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
@Table(name = "venta")
public class Venta extends AuditoriaRevision {
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_id_empleado", foreignKey = @ForeignKey(name = "venta_realizado_por_empleado"))
  private Empleado empleado;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_id_cliente", foreignKey = @ForeignKey(name = "venta_pertenece_a_cliente"))
  private Cliente cliente;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "nro_venta")
  private Integer nroVenta;

  @Column(name = "fecha_venta", nullable = false)
  private LocalDateTime fechaVenta;

  @Column(name = "total_venta", nullable = false)
  private Float totalVenta;

  @Column(name = "total_desc_venta")
  private Float totalDescVenta;
}
