package com.tiendadbii.tiendadbii.model.repository;

import com.tiendadbii.tiendadbii.model.entity.Ocupa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OcupaRepository extends JpaRepository<Ocupa, Integer> {
  List<Ocupa> findAllByEmpleadoIdEmpleado(Integer idEmpleado);
}