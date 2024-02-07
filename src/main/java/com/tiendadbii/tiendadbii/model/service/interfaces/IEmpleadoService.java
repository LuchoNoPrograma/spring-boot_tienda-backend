package com.tiendadbii.tiendadbii.model.service.interfaces;

import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.service.base.GenericCrudService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmpleadoService extends GenericCrudService<Empleado, Integer> {
  List<Empleado> findAll(Pageable pageable);

  Empleado createNew(Empleado entity, List<Cargo> listaCargo);

  Empleado update(Empleado entity, List<Cargo> listaCargo);
}
