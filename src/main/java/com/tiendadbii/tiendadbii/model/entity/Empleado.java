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
@Table(name = "empleado")
public class Empleado extends AuditoriaRevision{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idEmpleado;

  private String nombreEmp;
  private String apPaternoEmp;
  private String apMaternoEmp;
  private String celularEmp;
  private String emailEmp;
  private String direccionEmp;
}
