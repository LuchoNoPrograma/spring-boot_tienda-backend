package com.tiendadbii.tiendadbii.model.service.interfaces;

import com.tiendadbii.tiendadbii.model.entity.Producto;
import com.tiendadbii.tiendadbii.model.service.base.GenericCrudService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IProductoService extends GenericCrudService<Producto, Integer> {
  List<Producto> findAll(Sort sort);

  List<Producto> findAll(Pageable pageable);

  Producto findByCodigoBarra(String codigoBarra);
}
