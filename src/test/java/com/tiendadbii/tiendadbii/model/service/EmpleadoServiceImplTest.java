package com.tiendadbii.tiendadbii.model.service;

import com.tiendadbii.tiendadbii.TiendaDbiiApplication;
import com.tiendadbii.tiendadbii.config.ContainerEnvironment;
import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.entity.Horario;
import com.tiendadbii.tiendadbii.model.entity.Ocupa;
import com.tiendadbii.tiendadbii.model.repository.CargoRepository;
import com.tiendadbii.tiendadbii.model.repository.EmpleadoRepository;
import com.tiendadbii.tiendadbii.model.repository.HorarioRepository;
import com.tiendadbii.tiendadbii.model.repository.OcupaRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICargoService;
import com.tiendadbii.tiendadbii.model.service.interfaces.IEmpleadoService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import static org.springframework.util.Assert.*;

@Log4j2
@SpringBootTest(classes = TiendaDbiiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class EmpleadoServiceImplTest extends ContainerEnvironment {
  @Autowired
  private EmpleadoRepository empleadoRepository;

  @Autowired
  private OcupaRepository ocupaRepository;

  @Autowired
  private HorarioRepository horarioRepository;

  @Autowired
  private IEmpleadoService empleadoService;

  @Autowired
  private ICargoService cargoService;


  @Test
  public void givenEmpleado_whenCreate_thenReturnPersistedEmpleado() {
    //Given: Empleado must have at least 1 ocupa, at least 1 cargo, and all properties except id
    Empleado empleado = this.initEmpleado();

    //When: Empleado is created and inserted in DB, will persist by CASCADE on Ocupa and Horario
    Empleado persistedEmpleado = empleadoService.createNew(empleado);

    //Then: Verify if Empleado has Ocupa and Horario, with all properties included id, and fechaRegistro
    log.info("Logging persistedEmpleado: {}", persistedEmpleado);
    notNull(persistedEmpleado.getIdEmpleado(), "Returned id null");
    notNull(persistedEmpleado.getFechaRegistro(), "Returned fechaRegistro null");
    notEmpty(persistedEmpleado.getListaOcupa(), "Returned listaOcupa null");
    notEmpty(persistedEmpleado.getListaHorario(), "Returned listaHorario null");

    isTrue(!persistedEmpleado.getListaOcupa().isEmpty(), "Returned listaOcupa must no be empty");
    persistedEmpleado.getListaOcupa().forEach(ocupa -> {
      notNull(ocupa.getEmpleado(), "Returned Empleado null");
    });

    isTrue(!persistedEmpleado.getListaHorario().isEmpty(), "Returned listaHorario must not be empty");
    persistedEmpleado.getListaHorario().forEach(horario -> {
      notNull(horario.getEmpleado(), "Returned Empleado null");
    });
  }

  @Test
  public void givenEmpleadoExistent_whenUpdate_thenReturnUpdatedEmpleado() {
    //Given: Empleado must be created in DB with Horario and Ocupa
    Cargo cargoCajero = cargoService.createNew(Cargo.builder().nombreCargo("Cajero/a").build());

    List<Ocupa> listaOcupa = List.of(
      Ocupa.builder().cargo(cargoCajero).fechaInicio(LocalDate.now()).fechaFin(LocalDate.now()).build()
    );
    List<Horario> listaHorario = List.of(
      Horario.builder().dia("Sábado").horaIngreso(LocalTime.of(7, 0)).horaSalida(LocalTime.of(16, 0)).build(),
      Horario.builder().dia("Domingo").horaIngreso(LocalTime.of(7, 0)).horaSalida(LocalTime.of(16, 0)).build()
    );


    Empleado empleadoCreated = empleadoService.createNew(this.initEmpleado());

    empleadoCreated.setCi("987654321");
    empleadoCreated.setListaOcupa(listaOcupa);
    empleadoCreated.setListaHorario(listaHorario);

    //When: Empleado is updated, listaHorario and lisatOcupa will be overrided
    Empleado updatedEmpleado = empleadoService.update(empleadoCreated);
    log.info("Logging empleadoUpdated: {}", updatedEmpleado);
    log.info("Logging listaOcupa: {}", ocupaRepository.findAllByEmpleadoIdEmpleado(updatedEmpleado.getIdEmpleado()));
    log.info("Logging listaHorario: {}", horarioRepository.findAllByEmpleadoIdEmpleado(updatedEmpleado.getIdEmpleado()));

    //Then verify if Empleado has been updated with overrided relationship
    //Expected values ListOcupa -> Ocupa = {idCargo = 1, Cajero/a}
    //Expected values ListHorario -> Horario = {Sábado, 7:00, 16:00}, {Domingo, 7:00, 16:00}

    isTrue(updatedEmpleado.getCi().equals("987654321"), "Returned ci must be 987654321");
    notNull(updatedEmpleado.getListaOcupa(), "Returned listaOcupa null");
    notNull(updatedEmpleado.getListaHorario(), "Returned listaHorario null");

    isTrue(!updatedEmpleado.getListaOcupa().isEmpty(), "Returned listaOcupa must not be empty");
    /*isTrue(updatedEmpleado.getListaOcupa().stream().noneMatch(ocupa -> ocupa.getEmpleado().getIdEmpleado() == null), "All ocupa must have Empleado");*/
    //Define a custom comparator fechaInicio, fechaFin, cargo.nombreCargo
    Comparator<Ocupa> ocupaComparator = Comparator.comparing(Ocupa::getFechaInicio).thenComparing(Ocupa::getFechaFin).thenComparing(ocupa -> ocupa.getCargo().getNombreCargo());
    Assertions.assertThat(updatedEmpleado.getListaOcupa()).usingElementComparator(ocupaComparator).containsExactlyInAnyOrderElementsOf(listaOcupa);


    isTrue(!updatedEmpleado.getListaHorario().isEmpty(), "Returned listaHorario must not be empty");
    /*isTrue(updatedEmpleado.getListaHorario().stream().noneMatch(horario -> horario.getEmpleado().getIdEmpleado() == null), "All horario must have Empleado");*/
    Comparator<Horario> horarioComparator = Comparator.comparing(Horario::getDia).thenComparing(Horario::getHoraIngreso).thenComparing(Horario::getHoraSalida);
    Assertions.assertThat(updatedEmpleado.getListaHorario()).usingElementComparator(horarioComparator).containsExactlyInAnyOrderElementsOf(listaHorario);
  }

  @Test
  public void givenEmpleado_whenDeleteById_thenDelete() {
    //Given:
    Empleado persistedEmpleado = empleadoService.createNew(this.initEmpleado());

    //When:
    empleadoService.deleteById(persistedEmpleado.getIdEmpleado());

    //Then: Verify if Empleado has been removed included all Ocupa and Horario
    Empleado deletedEmpleado = empleadoService.findById(persistedEmpleado.getIdEmpleado());
    isNull(deletedEmpleado, "Empleado was not deleted");

    List<Ocupa> deletedOcupaList = ocupaRepository.findAllByEmpleadoIdEmpleado(persistedEmpleado.getIdEmpleado());
    isTrue(deletedOcupaList.isEmpty(), "Ocupa list was not deleted");

    List<Horario> deletedHorarioList = horarioRepository.findAllByEmpleadoIdEmpleado(persistedEmpleado.getIdEmpleado());
    isTrue(deletedHorarioList.isEmpty(), "Horario list was not deleted");
  }

  private Empleado initEmpleado() {
    Cargo cargo = Cargo.builder().nombreCargo("Vendedor/a").build();
    Cargo cargoPersisted = cargoService.createNew(cargo);

    List<Ocupa> listaOcupa = List.of(new Ocupa(cargoPersisted, null, null, LocalDate.now(), LocalDate.now().plusYears(1)));
    List<Horario> listaHorario = List.of(
      new Horario(null, null, "Lunes", LocalTime.of(7, 0), LocalTime.of(14, 0)),
      new Horario(null, null, "Martes", LocalTime.of(7, 0), LocalTime.of(14, 0)),
      new Horario(null, null, "Miercoles", LocalTime.of(7, 0), LocalTime.of(14, 0)),
      new Horario(null, null, "Jueves", LocalTime.of(7, 0), LocalTime.of(14, 0)),
      new Horario(null, null, "Viernes", LocalTime.of(7, 0), LocalTime.of(14, 0))
    );

    Empleado empleado = Empleado.builder()
      .ci("123456789")
      .nombres("Luis")
      .apellidos("Morales")
      .direccion("La libertad")
      .prefijoCelular("+591")
      .celular("12345678")
      .listaOcupa(listaOcupa)
      .listaHorario(listaHorario)
      .email("example@gmail.com")
      .build();

    empleado.getListaOcupa().forEach(ocupa -> ocupa.setEmpleado(empleado));
    empleado.getListaHorario().forEach(horario -> horario.setEmpleado(empleado));

    return empleado;
  }
}
