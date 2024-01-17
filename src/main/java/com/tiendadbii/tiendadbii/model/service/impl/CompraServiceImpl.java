package com.tiendadbii.tiendadbii.model.service.impl;

import com.tiendadbii.tiendadbii.model.entity.Compra;
import com.tiendadbii.tiendadbii.model.entity.DetalleCompra;
import com.tiendadbii.tiendadbii.model.entity.Producto;
import com.tiendadbii.tiendadbii.model.repository.CompraRepository;
import com.tiendadbii.tiendadbii.model.repository.ProductoRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements ICompraService {
  private final CompraRepository compraRepository;
  private final ProductoRepository productoRepository;

  @Override
  public List<Compra> findAll() {
    return compraRepository.findAll(Sort.by("fechaCompra").descending());
  }

  @Transactional
  @Override
  public Compra createNew(Compra entity) {
    /*compra.setTotalCompra((float) compra.getListaDetalleCompra().stream().mapToDouble(DetalleCompra::getSubtotalDetalle).sum());
    compra.setTotalDescCompra((float) compra.getListaDetalleCompra().stream().mapToDouble(DetalleCompra::getSubtotalDescDetalle).sum());*/
    entity.setTotalCompra((float) entity.getListaDetalleCompra().stream().mapToDouble(DetalleCompra::getSubtotalDetalle).sum());
    entity.setTotalDescCompra((float) entity.getListaDetalleCompra().stream().mapToDouble(DetalleCompra::getSubtotalDescDetalle).sum());
    entity.getListaDetalleCompra().forEach(d -> {
      if (d.getProducto() != null && d.getProducto().getCodigoProducto() != null) {
        productoRepository.findByCodigoProducto(d.getProducto().getCodigoProducto()).ifPresent(producto -> {
          producto.setStock(producto.getStock() + d.getCantidad());
          d.setProducto(producto);
        });
      }
    });
    Compra compra = compraRepository.save(entity);
    return compra;
  }

  @Override
  public Compra update(Compra entity) {
    return null;
  }

  @Override
  public void deleteById(Integer id) {

  }

  @Override
  public Compra findById(Integer id) {
    return null;
  }
}
