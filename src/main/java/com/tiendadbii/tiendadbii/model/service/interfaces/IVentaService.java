package com.tiendadbii.tiendadbii.model.service.interfaces;

import com.tiendadbii.tiendadbii.dto.VentaResumenDto;
import com.tiendadbii.tiendadbii.model.entity.Venta;
import com.tiendadbii.tiendadbii.model.service.base.GenericCrudService;
import jakarta.persistence.Tuple;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IVentaService extends GenericCrudService<Venta, Integer> {
  List<Venta> findByFechaVentaBetween(LocalDate start, LocalDate end);

  List<VentaResumenDto> getVentaResumenBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
