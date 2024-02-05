package com.tiendadbii.tiendadbii.model.service.interfaces;

import com.tiendadbii.tiendadbii.model.entity.Compra;
import com.tiendadbii.tiendadbii.model.service.base.GenericCrudService;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICompraService extends GenericCrudService<Compra, Integer> {
  List<Compra> findAll(Pageable pageable);

  @Transactional
  Compra createNew(Compra entity, Integer idProveedor);

  List<Compra> findAllByProveedorIdProveedor(Integer idProveedor);
}
