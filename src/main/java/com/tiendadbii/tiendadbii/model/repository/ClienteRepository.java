package com.tiendadbii.tiendadbii.model.repository;

import com.tiendadbii.tiendadbii.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}