package com.tiendadbii.tiendadbii.model.service.interfaces;

import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.service.base.GenericCrudService;

public interface IEmpleadoService extends GenericCrudService<Empleado, Integer> {
  Empleado createNewEmployee(Empleado employee);
}
