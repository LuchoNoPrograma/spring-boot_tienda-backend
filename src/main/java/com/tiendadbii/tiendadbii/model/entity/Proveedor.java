package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.Persona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedor")
public class Proveedor extends Persona {
  //Por defecto el fetch es LAZY en @OneToMany
  @OneToMany(mappedBy = "proveedor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Compra> listaCompra;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_proveedor")
  private Integer idProveedor;

  @Column(name = "email", length = 55)
  private String email;
}
