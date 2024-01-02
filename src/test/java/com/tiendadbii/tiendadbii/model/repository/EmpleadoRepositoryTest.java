package com.tiendadbii.tiendadbii.model.repository;

import com.tiendadbii.tiendadbii.TiendaDbiiApplication;
import com.tiendadbii.tiendadbii.config.ContainerEnvironment;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = TiendaDbiiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class EmpleadoRepositoryTest extends ContainerEnvironment {
  @Autowired
  private EmpleadoRepository empleadoRepository;

  @Test
  //Here's a simple test designed to verify non-null values.
  //It's worth noting that the database schema already contains configured validations for these columns.
  public void givenEmployee_whenSave_thenReturnPersistedEmpleado() {
    //Given: Empleado before save, must have the following attributes:
    //nombres, apellidos, ci, prefijoCelular, celular
    Empleado empleado = new Empleado();
    empleado.setNombres("Luis");
    empleado.setApellidos("Perez");
    empleado.setCi("123456789");
    empleado.setPrefijoCelular("+591");
    empleado.setCelular("12345678");

    //When: Empleado is saved(persist or update) SpringBot creates SQL and verifies if an Empleado has not null values in DB
    Empleado savedEmploye = empleadoRepository.save(empleado);

    //Then: Verify if these attributes are not null
    Assert.notNull(savedEmploye.getNombres(), "nombres");
    Assert.notNull(savedEmploye.getApellidos(), "apellidos");
    Assert.notNull(savedEmploye.getCi(), "ci");
    Assert.notNull(savedEmploye.getPrefijoCelular(), "prefijoCelular");
    Assert.notNull(savedEmploye.getCelular(), "celular");
  }
}
