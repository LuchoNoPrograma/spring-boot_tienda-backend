package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.Persona;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedor")
public class Proveedor extends Persona {
  //Por defecto el fetch es LAZY en @OneToMany
  @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
  private List<Compra> listaCompra;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_proveedor")
  private Integer idProveedor;

  @Column(name = "email", length = 55)
  private String email;

  @Builder
  public Proveedor(String ci, String nombres, String apellidos, String direccion, String celular, String prefijoCelular, List<Compra> listaCompra, Integer idProveedor, String email, LocalDateTime fechaRegistro) {
    super(ci, nombres, apellidos, direccion, celular, prefijoCelular);
    this.listaCompra = listaCompra;
    this.idProveedor = idProveedor;
    this.email = email;
    setFechaRegistro(fechaRegistro);
  }
}
