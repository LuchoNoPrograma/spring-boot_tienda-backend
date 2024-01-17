package com.tiendadbii.tiendadbii.model.repository;

import com.tiendadbii.tiendadbii.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

  Optional<Producto> findByCodigoBarra(String codigoBarra);

  Optional<Producto> findByCodigoProducto(Integer codigoProducto);
}