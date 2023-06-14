package com.tiendadbii.tiendadbii.model.repository;

import com.tiendadbii.tiendadbii.model.Estado;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
  @Query("SELECT e FROM Empleado e WHERE e.estado = ?1")
  List<Empleado> findAllByEstado(Estado estado);
}