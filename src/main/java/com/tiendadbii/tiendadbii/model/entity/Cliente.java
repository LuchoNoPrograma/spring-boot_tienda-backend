package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.Persona;
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
public class Cliente extends Persona {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_cliente")
  private Integer idCliente;

  @Column(name = "email", length = 55)
  private String email;
}
