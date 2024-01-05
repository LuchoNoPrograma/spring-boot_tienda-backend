package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.entity.parent.Persona;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@Table(name = "empleado")
public class Empleado extends Persona {
  @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Horario> listaHorario;

  @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Ocupa> listaOcupa;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_empleado")
  private Integer idEmpleado;

  @Column(name = "email", length = 55)
  private String email;

  @Builder
  public Empleado(String ci, String nombres, String apellidos, String direccion, String celular, String prefijoCelular, List<Horario> listaHorario, List<Ocupa> listaOcupa, Integer idEmpleado, String email, LocalDateTime fechaRegistro) {
    super(ci, nombres, apellidos, direccion, celular, prefijoCelular);
    this.listaHorario = listaHorario;
    this.listaOcupa = listaOcupa;
    this.idEmpleado = idEmpleado;
    this.email = email;
    this.setFechaRegistro(fechaRegistro);
  }
}
