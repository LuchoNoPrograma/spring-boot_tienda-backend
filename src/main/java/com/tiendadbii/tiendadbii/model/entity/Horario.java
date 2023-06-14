package com.tiendadbii.tiendadbii.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horario")
public class Horario extends AuditoriaRevision{
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "empleado_id_empleado")
  private Empleado empleado;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idHorario;
  private String dia;
  private LocalDateTime horaIngreso;
  private LocalDateTime horaSalida;
}
