package com.tiendadbii.tiendadbii.model.service.impl;

import com.tiendadbii.tiendadbii.model.entity.Producto;
import com.tiendadbii.tiendadbii.model.repository.ProductoRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {
  private final ProductoRepository productoRepository;
  @Override
  public List<Producto> findAll() {
    return productoRepository.findAll(Sort.by("nombreProducto").ascending());
  }

  @Override
  public List<Producto> findAll(Sort sort) {
    return productoRepository.findAll(sort);
  }

  @Override
  public List<Producto> findAll(Pageable pageable) {
    return productoRepository.findAll(pageable).getContent();
  }

  @Override
  public Producto createNew(Producto entity) {
    return productoRepository.save(entity);
  }

  @Override
  public Producto update(Producto entity) {
    return productoRepository.save(entity);
  }

  @Override
  public void deleteById(Integer id) {

  }

  @Override
  public Producto findById(Integer id) {
    return productoRepository.findById(id).orElse(null);
  }

  @Override
  public Producto findByCodigoBarra(String codigoBarra) {
    return productoRepository.findByCodigoBarra(codigoBarra).orElse(null);
  }
}
