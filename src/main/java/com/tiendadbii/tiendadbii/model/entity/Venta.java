package com.tiendadbii.tiendadbii.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venta")
public class Venta extends AuditoriaRevision{
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "empleado_id_empleado")
  private Empleado empleado;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_ci_cliente")
  private Cliente cliente;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idVenta;
  private Integer numeroVenta;
  private LocalDateTime fechaVenta;
  private Double totalVenta;
  private Double totalDescVenta;
}
