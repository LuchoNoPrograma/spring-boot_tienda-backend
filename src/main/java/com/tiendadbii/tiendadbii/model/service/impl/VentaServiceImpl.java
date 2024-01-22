package com.tiendadbii.tiendadbii.model.service.impl;

import com.tiendadbii.tiendadbii.model.entity.DetalleVenta;
import com.tiendadbii.tiendadbii.model.entity.Venta;
import com.tiendadbii.tiendadbii.model.repository.ClienteRepository;
import com.tiendadbii.tiendadbii.model.repository.DetalleVentaRepository;
import com.tiendadbii.tiendadbii.model.repository.ProductoRepository;
import com.tiendadbii.tiendadbii.model.repository.VentaRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.IVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements IVentaService {
  private final VentaRepository ventaRepository;
  private final ClienteRepository clienteRepository;
  private final DetalleVentaRepository detalleVentaRepository;
  private final ProductoRepository productoRepository;

  @Override
  public List<Venta> findAll() {
    return ventaRepository.findAll(Sort.by("fechaVenta").descending());
  }

  @Override
  @Transactional
  public Venta createNew(Venta entity) {
    if (entity.getCliente().getIdCliente() != null) {
      clienteRepository.findById(entity.getCliente().getIdCliente()).ifPresent(entity::setCliente);
    }

    float totalVenta = 0;
    float totalDescVenta = 0;
    List<DetalleVenta> listaDetalleVenta = new ArrayList<>();

    for (DetalleVenta detalleVenta : entity.getListaDetalleVenta()) {
      //Pretty interesting, if producto has an id existent in the DB, all fields of the entity will be null except by the id,
      //so, this is the reason why we are finding it in the DB to get all field values, Hibernate state managed
      productoRepository.findById(detalleVenta.getProducto().getCodigoProducto()).ifPresent(producto -> {
        //System.out.println(producto);
        detalleVenta.setSubtotalDetalle((double) (detalleVenta.getCantidad() * producto.getPrecioVenta()));
        producto.setStock(producto.getStock() - detalleVenta.getCantidad());
        detalleVenta.setVenta(null);
        listaDetalleVenta.add(detalleVenta);
      });

      totalVenta += detalleVenta.getSubtotalDetalle();
      totalDescVenta += detalleVenta.getSubtotalDescDetalle();
    }

    detalleVentaRepository.saveAll(listaDetalleVenta);

    entity.setTotalVenta(totalVenta);
    entity.setTotalDescVenta(totalDescVenta);

    //Passed to a persistent object, so any changes will be updated in the DB automatically without use ventaRepository#save
    Venta ventaPersisted = ventaRepository.save(entity);

    //Interesting, if I comment this line code, it won't update DetalleVenta with ForeignKey of Venta
    //Why are updating in the DB? if I don't save it with detalleVentaRepository#saveAll
    //Ok, I read more about it in the documentation: https://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html/ch11.html#objectstate-modifying
    listaDetalleVenta.forEach(detalleVenta -> detalleVenta.setVenta(ventaPersisted));

    /*ventaPersisted.setTotalDescVenta(1f);*/ //<- Yep, it updates it with the value of totalDescVenta=1 even if I don't save it, all changes are saved when session is flushed
    ventaPersisted.setListaDetalleVenta(listaDetalleVenta);
    return ventaPersisted;
  }

  @Override
  public Venta update(Venta entity) {
    return null;
  }

  @Override
  public void deleteById(Integer id) {

  }

  @Override
  public Venta findById(Integer id) {
    return ventaRepository.findById(id).orElse(null);
  }
}
