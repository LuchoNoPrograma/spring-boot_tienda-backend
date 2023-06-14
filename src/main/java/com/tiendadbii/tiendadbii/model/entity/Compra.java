package com.tiendadbii.tiendadbii.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compra")
public class Compra extends AuditoriaRevision{
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "proveedor_id_proveedor")
  private Proveedor proveedor;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idCompra;
  private LocalDateTime fechaCompra;
  private Double totalCompra;
}
