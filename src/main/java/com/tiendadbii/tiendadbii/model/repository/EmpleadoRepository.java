package com.tiendadbii.tiendadbii.model.repository;

import com.tiendadbii.tiendadbii.model.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}