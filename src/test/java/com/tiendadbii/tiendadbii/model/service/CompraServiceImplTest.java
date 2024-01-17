package com.tiendadbii.tiendadbii.model.service;

import com.tiendadbii.tiendadbii.TiendaDbiiApplication;
import com.tiendadbii.tiendadbii.config.ContainerEnvironment;
import com.tiendadbii.tiendadbii.model.entity.Compra;
import com.tiendadbii.tiendadbii.model.entity.DetalleCompra;
import com.tiendadbii.tiendadbii.model.entity.Producto;
import com.tiendadbii.tiendadbii.model.entity.Proveedor;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICompraService;
import com.tiendadbii.tiendadbii.model.service.interfaces.IProductoService;
import com.tiendadbii.tiendadbii.model.service.interfaces.IProveedorService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

@Log4j2
@SpringBootTest(classes = TiendaDbiiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CompraServiceImplTest extends ContainerEnvironment {
  @Autowired
  private ICompraService compraService;

  @Autowired
  private IProveedorService proveedorService;

  @Autowired
  private IProductoService productoService;

  @Test
  public void givenCompraWithDetalleCompraHasNotExistentProducto_whenCreate_thenReturnPersistedCompra() {
    //Given
    Proveedor proveedor = proveedorService.createNew(initProveedor());
    Compra compra = Compra.builder()
      .proveedor(proveedor)
      .fechaCompra(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 30))
      .listaDetalleCompra(List.of(
        DetalleCompra.builder()
          .producto(initListaProductos().get("Vino"))
          .fechaDetalle(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 30))
          .cantidad(initListaProductos().get("Vino").getStock())
          .subtotalDetalle(2000F)
          .subtotalDescDetalle(0F)
          .build(),
        DetalleCompra.builder()
          .producto(initListaProductos().get("Gaseosa"))
          .fechaDetalle(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 30))
          .cantidad(initListaProductos().get("Gaseosa").getStock())
          .subtotalDetalle(1000F)
          .subtotalDescDetalle(100F)
          .build()
      ))
      .build();

    //When create a Compra, and if the Producto isn't registered in DB, automatically will create Producto in DetalleCompra
    Compra createdCompra = compraService.createNew(compra);

    //Then Verify the output, Producto and total amount, stock
    Assert.notNull(createdCompra.getIdCompra());
    Assert.notNull(createdCompra.getFechaRegistro());
    Assert.notNull(proveedor.getIdProveedor());
    Assert.isTrue(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 30).equals(createdCompra.getFechaCompra()));

    Assert.notNull(createdCompra.getListaDetalleCompra());
    Assert.isTrue(createdCompra.getListaDetalleCompra().size() == 2);
    Assert.notNull(createdCompra.getListaDetalleCompra().get(0));

    Producto productoVino = productoService.findByCodigoBarra("00001");
    Producto productoGaseosa = productoService.findByCodigoBarra("00002");

    //Verify if the Vino stock is the same before and after created
    Assert.notNull(productoVino, "Returned producto codigoBarra: '00001' null");
    //stock and cantidad must be equal = 100
    Assert.isTrue(productoVino.getStock() == 100);
    Assert.isTrue(createdCompra.getListaDetalleCompra().get(0).getSubtotalDetalle() == 2000F);
    Assert.isTrue(createdCompra.getListaDetalleCompra().get(0).getSubtotalDescDetalle() == 0F);

    //Verify if the Gaseosa stock is the same before and after created
    Assert.notNull(productoGaseosa, "Returned producto codigoBarra: '00002' null");
    //stock and cantidad must be equal = 50
    Assert.isTrue(productoGaseosa.getStock() == 50);
    Assert.isTrue(createdCompra.getListaDetalleCompra().get(1).getSubtotalDetalle() == 1000F);
    Assert.isTrue(createdCompra.getListaDetalleCompra().get(1).getSubtotalDescDetalle() == 100F);

    //Verify if the total amount of DetalleCompra is equal in Compra
    //total must be 3000 (2000 + 1000)
    //total desc must be 100 (0 + 100)
    Assert.isTrue(createdCompra.getTotalCompra() == 3000);
    Assert.isTrue(createdCompra.getTotalDescCompra() == 100);
  }

  @Test
  public void givenCompraWithDetalleCompraHasExistentAndNonexistentProducto_whenCreate_thenReturnPersistedCompra() {
    //Given
    Proveedor proveedor = proveedorService.createNew(initProveedor());
    productoService.createNew(initListaProductos().get("Vino"));

    Producto productoVinoFound = productoService.findByCodigoBarra("00001");

    Compra compra = Compra.builder()
      .proveedor(proveedor)
      .fechaCompra(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 30))
      .listaDetalleCompra(List.of(
        DetalleCompra.builder()
          .producto(productoVinoFound)
          .fechaDetalle(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 30))
          .cantidad(50)
          .subtotalDetalle(1000F)
          .subtotalDescDetalle(0F)
          .build(),
        DetalleCompra.builder()
          .producto(initListaProductos().get("Gaseosa"))
          .fechaDetalle(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 30))
          .cantidad(50)
          .subtotalDetalle(1000F)
          .subtotalDescDetalle(100F)
          .build()
      ))
      .build();

    //When create a Compra, and if the Producto is registered in DB,
    // will not create Producto in DetalleCompra, just only update the stock of Producto
    Compra compraCreated = compraService.createNew(compra);

    //Then Verify the output, Producto and total amount, stock. Stock must be updated based on the amount of cantidad in DetalleCompra
    //Verify if the Vino stock is the same before and after created

    Producto productoVinoUpdated = productoService.findByCodigoBarra("00001");
    Producto productoGaseosaUpdated = productoService.findByCodigoBarra("00002");
    //100 at the first time, 50 at the second time -> 100+50=150
    Assert.isTrue(LocalDateTime.of(2021, Month.JANUARY, 1, 8, 30).equals(compraCreated.getFechaCompra()));
    Assert.isTrue(productoVinoUpdated.getStock() == 150);
    Assert.isTrue(compraCreated.getListaDetalleCompra().get(0).getSubtotalDetalle() == 1000F);
    Assert.isTrue(compraCreated.getListaDetalleCompra().get(0).getSubtotalDescDetalle() == 0F);

    //stock and cantidad must be equal = 50
    Assert.isTrue(productoGaseosaUpdated.getStock() == 50);
    Assert.isTrue(compraCreated.getListaDetalleCompra().get(1).getSubtotalDetalle() == 1000F);
    Assert.isTrue(compraCreated.getListaDetalleCompra().get(1).getSubtotalDescDetalle() == 100F);

    //Verify if the total amount of DetalleCompra is equal in Compra
    //total must be 2000 (1000 + 1000)
    //total desc must be 100 (0 + 100)
    Assert.isTrue(compraCreated.getTotalCompra() == 2000);
    Assert.isTrue(compraCreated.getTotalDescCompra() == 100);
  }

  private Proveedor initProveedor() {
    return Proveedor.builder()
      .nombres("Luis")
      .apellidos("Torres")
      .ci("123456")
      .prefijoCelular("+56")
      .celular("9248984242")
      .email("luis@tienda.com")
      .build();
  }

  private Map<String, Producto> initListaProductos() {
    return Map.of("Vino", Producto.builder()
        .nombreProducto("VINO TEST 1L")
        .codigoBarra("00001")
        .descripcion("Marca 1, 15% Alcohol")
        .precioProducto(20F)
        .precioVenta(22.5F)
        .stock(100)
        .build(),
      "Gaseosa", Producto.builder()
        .nombreProducto("GASEOSA TEST 2L")
        .codigoBarra("00002")
        .descripcion("Marca 2")
        .precioProducto(10F)
        .precioVenta(12F)
        .stock(50)
        .build()
    );
  }
}
