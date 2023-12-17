package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.AuditoriaRevision;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compra")
public class Compra extends AuditoriaRevision {
  @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
  private List<DetalleCompra> listaDetalleCompra;

  //por defecto el fetch es EAGER en @ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_id_proveedor", foreignKey = @ForeignKey(name = "compra_suministrado_por_proveedor"))
  private Proveedor proveedor;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_compra")
  private Integer idCompra;

  @Column(name = "fecha_compra", nullable = false)
  private LocalDateTime fechaCompra;

  @Column(name = "total_compra", nullable = false)
  private Float totalCompra;

  @Column(name = "total_desc_compra")
  private Float totalDescCompra;
}
