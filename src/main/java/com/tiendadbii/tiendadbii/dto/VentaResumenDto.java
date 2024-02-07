package com.tiendadbii.tiendadbii.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VentaResumenDto {
  LocalDate fecha;
  float ventaTotalBruto;
  float descuentoTotal;
  float ventaTotalNeto;
}
