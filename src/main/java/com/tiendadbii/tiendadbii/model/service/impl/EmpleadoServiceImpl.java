package com.tiendadbii.tiendadbii.model.service.impl;

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
  public List<Empleado> findAll(Pageable pageable){
    return empleadoRepository.findAll(pageable).get().toList();
  }

  @Override
  public Empleado createNew(Empleado entity) {
    return empleadoRepository.save(entity);
  }

  @Override
  public Empleado update(Empleado entity) {
    horarioRepository.deleteAll(horarioRepository.findAllByEmpleadoIdEmpleado(entity.getIdEmpleado()));
    ocupaRepository.deleteAll(ocupaRepository.findAllByEmpleadoIdEmpleado(entity.getIdEmpleado()));

    Empleado newEmpleado = empleadoRepository.save(entity);
    Empleado emptyEmpleado = new Empleado();
    emptyEmpleado.setIdEmpleado(newEmpleado.getIdEmpleado());

    List<Horario> listaHorario = entity.getListaHorario();
    listaHorario.forEach(horario -> horario.setEmpleado(emptyEmpleado));

    List<Ocupa> listaOcupa = entity.getListaOcupa();
    listaOcupa.forEach(ocupa -> ocupa.setEmpleado(emptyEmpleado));

    newEmpleado.setListaHorario(horarioRepository.saveAll(listaHorario));
    newEmpleado.setListaOcupa(ocupaRepository.saveAll(listaOcupa));
    return newEmpleado;
  }


  @Override
  public void deleteById(Integer id) {
    empleadoRepository.deleteById(id);
  }

  @Override
  public Empleado findById(Integer id) {
    return empleadoRepository.findById(id).orElse(null);
  }

}
