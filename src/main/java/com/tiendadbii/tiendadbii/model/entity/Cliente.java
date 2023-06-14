package com.tiendadbii.tiendadbii.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente extends AuditoriaRevision{
  @Id
  private Integer ciCliente;
  private String nombreCliente;
  private String direccionCliente;
  private String celularCliente;
  private String emailCliente;
}
