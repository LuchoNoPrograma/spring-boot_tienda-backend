package com.tiendadbii.tiendadbii.model.service.impl;

import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.repository.EmpleadoRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.IEmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements IEmpleadoService {
  private final EmpleadoRepository empleadoRepository;
  @Override
  public List<Empleado> findAll() {
    return empleadoRepository.findAll(Sort.by("idEmpleado").descending());
  }

  @Override
  public Empleado save(Empleado entity) {
    return empleadoRepository.save(entity);
  }

  @Override
  public Optional<Empleado> findById(Integer id) {
    return empleadoRepository.findById(id);
  }

  @Override
  public void deleteById(Integer id) {
    empleadoRepository.deleteById(id);
  }

  @Override
  public Empleado createNewEmployee(Empleado employee) {
    return empleadoRepository.save(employee);
  }
}
