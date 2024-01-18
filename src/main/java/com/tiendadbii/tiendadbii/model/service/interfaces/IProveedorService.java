package com.tiendadbii.tiendadbii.model.service.interfaces;

import com.tiendadbii.tiendadbii.model.entity.Proveedor;
import com.tiendadbii.tiendadbii.model.service.base.GenericCrudService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProveedorService extends GenericCrudService<Proveedor, Integer> {
  List<Proveedor> findAll(Pageable pageable);
}
