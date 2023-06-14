package com.tiendadbii.tiendadbii.model.repository;

import com.tiendadbii.tiendadbii.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}