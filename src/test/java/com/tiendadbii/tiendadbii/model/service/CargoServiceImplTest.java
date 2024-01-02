package com.tiendadbii.tiendadbii.model.service;

import com.tiendadbii.tiendadbii.TiendaDbiiApplication;
import com.tiendadbii.tiendadbii.config.ContainerEnvironment;
import com.tiendadbii.tiendadbii.model.Estado;
import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.repository.CargoRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICargoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Log4j2
@SpringBootTest(classes = TiendaDbiiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CargoServiceImplTest extends ContainerEnvironment {
  @Autowired
  private CargoRepository cargoRepository;
  @Autowired
  private ICargoService cargoService;

  //Persisted Cargo must have id, estado = Estado.ACTIVO and fechaRegistro = LocalDateTime.now()
  @Test
  public void givenCargo_whenCreate_thenReturnPersistedCargo() {
    //Given: Cargo submitted by user input
    Cargo cargo = Cargo.builder().nombreCargo("Administrador").descripcion("Test1").build();

    //When: Cargo is created and inserted in DB
    Cargo persistedCargo = cargoService.createNew(cargo);

    //Then: Return cargo inserted in DB with id, estado and fechaRegistro
    log.info("Logging persistedCargo: {}", persistedCargo);
    Assert.notNull(persistedCargo.getIdCargo(), "Returned id null");
    Assert.notNull(persistedCargo.getEstado(), "Returned estado null");
    Assert.notNull(persistedCargo.getFechaRegistro(), "Returned fechaRegistro null");
  }

  @Test
  public void whenFindAll_thenReturnListaCargo() {
    //Given: call method
    List<Cargo> listaCargo = List.of(
      Cargo.builder().nombreCargo("Administrador").descripcion("Test1").build(),
      Cargo.builder().nombreCargo("Vendedor").descripcion("Test2").build(),
      Cargo.builder().nombreCargo("Cajero").descripcion("Test3").build()
    );
    listaCargo.forEach(cargo -> cargoService.createNew(cargo));

    //When: find all Cargo in DB without any condition
    List<Cargo> result = cargoService.findAll();

    //Then: Verify if the result is not empty
    log.info("Logging list: {}", result);
    Assert.notEmpty(result, "Result must not be empty");
  }

  /**
   * Updated Cargo must have id, NEW is Cargo submitted and OLD is Cargo in DB
   * NEW.estado = OLD.estado
   * NEW.fechaRegistro = OLD.fechaRegistro
   * Changes to 'estado' and 'fechaRegistro' made through user input will always be overwritten by the values retrieved from the Cargo in the database
  */
  @Test
  public void givenCargo_whenUpdate_thenReturnUpdatedCargo() {
    //Given: Cargo submitted by user input
    Cargo cargo = Cargo.builder().nombreCargo("Administrador").descripcion("Test1").build();
    Integer idPersisted = cargoService.createNew(cargo).getIdCargo();

    ///Necessary to retrieve Cargo from the DB because the attribute fechaRegistro from Cargo returned by cargoService.createNew has millisecond difference.
    //This disparity may lead to failure test when comparing these attributes, hence the need to query the DB again.
    Cargo persistedCargo = cargoRepository.findById(idPersisted).orElseThrow(() -> new RuntimeException("Cargo not found with id: " + idPersisted));

    log.info("Logging persistedCargo(OLD): {}", persistedCargo);

    Cargo submittedCargo = Cargo.builder().idCargo(persistedCargo.getIdCargo())
      .nombreCargo("Administrador Updated")
      .descripcion("Test1 Updated")
      .fechaRegistro(LocalDateTime.of(2002, Month.FEBRUARY, 11, 6, 30))
      .estado(Estado.ELIMINADO)
      .build();

    //When: Cargo is updated in the database, even if the 'fechaRegistro' and 'estado' are changed in the submitted Cargo.
    Cargo updatedCargo = cargoService.update(submittedCargo);

    //Then: Verify if these attributes are updated except [estado, fechaRegistro]
    log.info("Logging updatedCargo(NEW): {}", updatedCargo);
    Assert.isTrue(updatedCargo.getNombreCargo().equals(submittedCargo.getNombreCargo()), "nombreCargo is not updated");
    Assert.isTrue(updatedCargo.getDescripcion().equals(submittedCargo.getDescripcion()), "descripcion is not updated");

    Assert.isTrue(updatedCargo.getFechaRegistro().equals(persistedCargo.getFechaRegistro()), "fechaRegistro should not be updated");
    Assert.isTrue(updatedCargo.getEstado().equals(persistedCargo.getEstado()), "estado should not be updated");
  }

  @Test
  public void givenCargo_whenDeleted_thenUpdateWithEstadoEliminado() {
    //Given: Cargo submitted by user input
    Cargo cargo = Cargo.builder().nombreCargo("Administrador").descripcion("Test1").build();
    Integer idPersisted = cargoService.createNew(cargo).getIdCargo();

    //When: Cargo is "deleted" but not actually deleted in the database, only updated with estado=Estado.ELIMINADO
    cargoService.deleteById(idPersisted);

    //Then: Verify if the Cargo still exists in DB and if it was updated with estado=Estado.ELIMINADO
    Cargo foundCargo = cargoRepository.findById(idPersisted).orElseThrow(() -> new IllegalArgumentException("Cargo not found with id: " + idPersisted));
    log.info("Logging deletedCargo: {}", foundCargo);
    Assert.isTrue(foundCargo.getEstado().equals(Estado.ELIMINADO), "estado should be updated with Estado.ELIMINADO");
  }
}
