package com.tiendadbii.tiendadbii.model.service.impl;

import com.tiendadbii.tiendadbii.model.entity.Compra;
import com.tiendadbii.tiendadbii.model.entity.DetalleCompra;
import com.tiendadbii.tiendadbii.model.entity.Producto;
import com.tiendadbii.tiendadbii.model.repository.CompraRepository;
import com.tiendadbii.tiendadbii.model.repository.DetalleCompraRepository;
import com.tiendadbii.tiendadbii.model.repository.ProductoRepository;
import com.tiendadbii.tiendadbii.model.repository.ProveedorRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements ICompraService {
  private final CompraRepository compraRepository;
  private final ProductoRepository productoRepository;
  private final ProveedorRepository proveedorRepository;
  private final DetalleCompraRepository detalleCompraRepository;

  @Override
  public List<Compra> findAll() {
    return compraRepository.findAll(Sort.by("fechaCompra").descending());
  }

  @Override
  public Compra createNew(Compra entity) {
    return null;
  }

  @Override
  public List<Compra> findAll(Pageable pageable) {
    return compraRepository.findAll(pageable).getContent();
  }

  @Transactional
  @Override
  public Compra createNew(Compra entity, Integer idProveedor) {
    /*compra.setTotalCompra((float) compra.getListaDetalleCompra().stream().mapToDouble(DetalleCompra::getSubtotalDetalle).sum());
    compra.setTotalDescCompra((float) compra.getListaDetalleCompra().stream().mapToDouble(DetalleCompra::getSubtotalDescDetalle).sum());*/

    entity.setProveedor(proveedorRepository.findById(idProveedor).orElseThrow(() -> new RuntimeException("Proveedor not found with id:" + idProveedor)));
    entity.setTotalCompra((float) entity.getListaDetalleCompra().stream().mapToDouble(DetalleCompra::getSubtotalDetalle).sum());
    entity.setTotalDescCompra((float) entity.getListaDetalleCompra().stream().mapToDouble(DetalleCompra::getSubtotalDescDetalle).sum());
    List<DetalleCompra> listaDetalleCompra = entity.getListaDetalleCompra();
    entity.setListaDetalleCompra(null);

    Compra compra = compraRepository.save(entity);

    listaDetalleCompra.forEach(detalleCompra -> {
      if (detalleCompra.getProducto().getCodigoProducto() != null) {
        productoRepository.findByCodigoProducto(detalleCompra.getProducto().getCodigoProducto())
          .ifPresentOrElse(producto -> {
            producto.setStock(producto.getStock() + detalleCompra.getCantidad());
            Producto productoPersisted = productoRepository.save(producto);

            detalleCompra.setCompra(compra);
            detalleCompra.setProducto(productoPersisted);
            detalleCompraRepository.save(detalleCompra);
          }, () -> {
            detalleCompra.getProducto().setStock(detalleCompra.getCantidad());
            Producto productoPersisted = productoRepository.save(detalleCompra.getProducto());

            detalleCompra.setCompra(compra);
            detalleCompra.setProducto(productoPersisted);
            detalleCompraRepository.save(detalleCompra);
          });
      }
    });

    /*listaDetalleCompra.forEach(detalleCompra -> {
      detalleCompra.setCompra(null);
      detalleCompra.setProducto(null);
    });
    compra.setProveedor(null);
    compra.setListaDetalleCompra(listaDetalleCompra);*/
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
