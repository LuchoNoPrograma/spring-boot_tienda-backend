package com.tiendadbii.tiendadbii.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedor")
public class Proveedor extends AuditoriaRevision{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idProveedor;
  private String nombreProveedor;
  private String direccionProveedor;
  private String celularProveedor;
  private String emailProveedor;
}
