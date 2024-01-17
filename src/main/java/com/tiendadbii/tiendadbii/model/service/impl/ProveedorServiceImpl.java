package com.tiendadbii.tiendadbii.model.service.impl;

import com.tiendadbii.tiendadbii.model.entity.Proveedor;
import com.tiendadbii.tiendadbii.model.repository.CompraRepository;
import com.tiendadbii.tiendadbii.model.repository.ProveedorRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.IProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements IProveedorService {
  private final ProveedorRepository proveedorRepository;
  private final CompraRepository compraRepository;

  @Override
  public List<Proveedor> findAll() {
    return proveedorRepository.findAll(Sort.by("nombres", "apellidos", "ci").descending());
  }

  @Override
  public List<Proveedor> findAll(Pageable pageable) {
    return proveedorRepository.findAll(pageable).get().toList();
  }

  @Override
  public Proveedor createNew(Proveedor entity) {
    return proveedorRepository.save(entity);
  }

  @Override
  public Proveedor update(Proveedor entity) {
    return proveedorRepository.save(entity);
  }

  @Override
  public void deleteById(Integer id) {
    proveedorRepository.deleteById(id);
  }

  @Override
  public Proveedor findById(Integer id) {
    Proveedor proveedor = proveedorRepository.findById(id).orElse(null);
    if (proveedor != null) {
      proveedor.setListaCompra(compraRepository.findAllByProveedorIdProveedor(proveedor.getIdProveedor()));
    }
    return proveedor;
  }
}
