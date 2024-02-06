package com.tiendadbii.tiendadbii.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.views.VentaViews;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.tiendadbii.tiendadbii.model.entity.Venta}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaDto implements Serializable {
  @JsonView({VentaViews.ConListaDetalleVenta.class})
  @NotNull(message = "listaDetalleVenta cannot be null")
  @Valid
  private List<DetalleVentaDto> listaDetalleVenta;

  @NotNull(message = "cliente cannot be null")
  /*@Valid*/
  private ClienteDto cliente;

  private Integer nroVenta;
  
  @NotNull(message = "fechaVenta cannot be null")
  private LocalDateTime fechaVenta;

  /*@NotNull(message = "totalVenta cannot be null")*/
  private Float totalVenta;

  private Float totalDescVenta;
}