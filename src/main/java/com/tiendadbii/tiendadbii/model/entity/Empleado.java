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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")
public class Empleado extends Persona {
  @OneToMany(mappedBy = "empleado", cascade = CascadeType.MERGE)
  private List<Horario> listaHorario;

  @OneToMany(mappedBy = "empleado", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private List<Ocupa> listaOcupa;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_empleado")
  private Integer idEmpleado;

  @Column(name = "email", length = 55)
  private String email;
}
