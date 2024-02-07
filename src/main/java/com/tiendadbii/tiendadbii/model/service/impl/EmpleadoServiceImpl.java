package com.tiendadbii.tiendadbii.model.service.impl;

import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.entity.Horario;
import com.tiendadbii.tiendadbii.model.entity.Ocupa;
import com.tiendadbii.tiendadbii.model.repository.EmpleadoRepository;
import com.tiendadbii.tiendadbii.model.repository.HorarioRepository;
import com.tiendadbii.tiendadbii.model.repository.OcupaRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.IEmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements IEmpleadoService {
  private final EmpleadoRepository empleadoRepository;
  private final HorarioRepository horarioRepository;
  private final OcupaRepository ocupaRepository;

  @Override
  public List<Empleado> findAll() {
    return empleadoRepository.findAll(Sort.by("nombres", "apellidos", "ci").descending());
  }

  @Override
  public List<Empleado> findAll(Pageable pageable) {
    return empleadoRepository.findAll(pageable).get().toList();
  }

  @Override
  public Empleado createNew(Empleado entity) {
    return empleadoRepository.save(entity);
  }

  @Override
  public Empleado createNew(Empleado entity, List<Cargo> listaCargo) {
    Empleado empleado = empleadoRepository.save(entity);
    List<Ocupa> listaOcupa = empleado.getListaOcupa().stream().map(ocupa -> {
      ocupa.setCargo(listaCargo.stream().filter(cargo -> cargo.getIdCargo().equals(ocupa.getCargo().getIdCargo())).findFirst().get());
      return ocupa;
    }).toList();

    empleado.setListaOcupa(listaOcupa);
    return empleado;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public Empleado update(Empleado entity, List<Cargo> listaCargo) {
    boolean shouldUpdateListaHorario = entity.getListaHorario() != null && !entity.getListaHorario().isEmpty();
    boolean shouldUpdateListaOcupa = entity.getListaOcupa() != null && !entity.getListaOcupa().isEmpty();
    if(shouldUpdateListaHorario) {
      horarioRepository.deleteAll(horarioRepository.findAllByEmpleadoIdEmpleado(entity.getIdEmpleado()));
    }

    if(shouldUpdateListaOcupa) {
      ocupaRepository.deleteAll(ocupaRepository.findAllByEmpleadoIdEmpleado(entity.getIdEmpleado()));
    }

    Empleado newEmpleado = empleadoRepository.save(entity);
    Empleado emptyEmpleado = new Empleado();
    emptyEmpleado.setIdEmpleado(newEmpleado.getIdEmpleado());


    if(shouldUpdateListaHorario) {
      List<Horario> listaHorario = entity.getListaHorario();
      listaHorario.forEach(horario -> horario.setEmpleado(emptyEmpleado));
      newEmpleado.setListaHorario(horarioRepository.saveAll(listaHorario));
    }else{
      newEmpleado.setListaHorario(horarioRepository.findAllByEmpleadoIdEmpleado(newEmpleado.getIdEmpleado()));
    }

    if(shouldUpdateListaOcupa) {
      List<Ocupa> listaOcupa = entity.getListaOcupa();
      listaOcupa.forEach(ocupa -> ocupa.setEmpleado(emptyEmpleado));
      List<Ocupa> listaOcupaPersisted = ocupaRepository.saveAll(listaOcupa);
      /*
       * Esta linea solo rellena los datos del cargo, ya que al persistirlos no retorna los datos de los cargos
       * */
      newEmpleado.setListaOcupa(listaOcupaPersisted.stream().map(ocupa -> {
          ocupa.setCargo(listaCargo.stream().filter(cargo -> cargo.getIdCargo().equals(ocupa.getCargo().getIdCargo())).findFirst().get());
          return ocupa;
        }).toList()
      );
    }else{
      newEmpleado.setListaOcupa(ocupaRepository.findAllByEmpleadoIdEmpleado(newEmpleado.getIdEmpleado()));
    }
    return newEmpleado;
  }


  @Transactional(rollbackFor = Exception.class)
  @Override
  public Empleado update(Empleado entity) {
    return empleadoRepository.save(entity);
  }


  @Override
  public void deleteById(Integer id) {
    empleadoRepository.deleteById(id);
  }

  @Override
  public Empleado findById(Integer id) {
    Empleado empleado = empleadoRepository.findById(id).orElse(null);
    if (empleado != null) {
      empleado.setListaHorario(horarioRepository.findAllByEmpleadoIdEmpleado(empleado.getIdEmpleado()));
      empleado.setListaOcupa(ocupaRepository.findAllByEmpleadoIdEmpleado(empleado.getIdEmpleado()));
    }

    return empleado;
  }

}
