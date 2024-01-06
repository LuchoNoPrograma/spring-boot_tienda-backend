package com.tiendadbii.tiendadbii.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendadbii.tiendadbii.TiendaDbiiApplication;
import com.tiendadbii.tiendadbii.config.ContainerEnvironment;
import com.tiendadbii.tiendadbii.dto.EmpleadoDto;
import com.tiendadbii.tiendadbii.dto.HorarioDto;
import com.tiendadbii.tiendadbii.dto.OcupaDto;
import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICargoService;
import com.tiendadbii.tiendadbii.model.service.interfaces.IEmpleadoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest(classes = TiendaDbiiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EmpleadoApiTest extends ContainerEnvironment {
  @Autowired
  private ICargoService cargoService;

  @Autowired
  private IEmpleadoService empleadoService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void givenEmpleadoDto_whenCreateEmpleado_thenReturnPersistedEmpleado() throws Exception {
    //Given: Empleado must have at least 1 ocupa, at least 1 cargo, and all properties except id
    Cargo cargo = Cargo.builder().nombreCargo("Vendedor/a").build();
    Cargo cargoPersisted = cargoService.createNew(cargo);

    EmpleadoDto empleadoDto = EmpleadoDto.builder()
      .nombres("Luis")
      .apellidos("Morales")
      .ci("1234567")
      .direccion("Av Lomas")
      .prefijoCelular("+57")
      .celular("1234567")
      .email("correo@example.com")
      .listaHorario(List.of(HorarioDto.builder()
          .dia("Domingo")
          .horaIngreso(LocalTime.of(6, 0))
          .horaSalida(LocalTime.of(14, 0)).build())
      ).listaOcupa(List.of(OcupaDto.builder()
          .cargo(Cargo.builder().idCargo(cargoPersisted.getIdCargo()).build())
          .fechaInicio(LocalDate.now())
          .fechaFin(LocalDate.now().plusMonths(6)).build())
        ).build();

    String empleadoJSON = objectMapper.writeValueAsString(empleadoDto);

    //When: Perform to endpoint POST /api/empleado
    ResultActions response = mockMvc.perform(
      post("/api/empleado")
        .contentType(MediaType.APPLICATION_JSON)
        .content(empleadoJSON)
        .accept(MediaType.APPLICATION_JSON)
    );

    //Then: Verify the response
    response.andExpect(status().is(201 ));
    response.andDo(print());
  }


}
