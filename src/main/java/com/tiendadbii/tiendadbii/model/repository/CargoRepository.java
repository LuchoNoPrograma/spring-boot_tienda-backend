package com.tiendadbii.tiendadbii.model.repository;

import com.tiendadbii.tiendadbii.model.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}