package com.tiendadbii.tiendadbii.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendadbii.tiendadbii.TiendaDbiiApplication;
import com.tiendadbii.tiendadbii.config.ContainerEnvironment;
import com.tiendadbii.tiendadbii.dto.CargoDto;
import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICargoService;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.CoreMatchers;
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
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
@SpringBootTest(classes = TiendaDbiiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CargoApiTest extends ContainerEnvironment {
  @Autowired
  private ICargoService cargoService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void givenListaCargo_whenFindAllCargo_thenListaCargoDto() throws Exception {
    //Given: listaCargo created in DB and "delete" the last item
    List<Cargo> listaCargo = List.of(
      Cargo.builder().nombreCargo("Administrador").descripcion("Test1").build(),
      Cargo.builder().nombreCargo("Vendedor").descripcion("Test2").build(),
      Cargo.builder().nombreCargo("Cajero").descripcion("Test3").build(),
      Cargo.builder().nombreCargo("Cajero eliminado").descripcion("Test3").build()
    );

    List<Cargo> listaCargoPersisted = listaCargo.stream().map(cargoService::createNew).toList();
    cargoService.deleteById(listaCargoPersisted.get(3).getIdCargo());

    //When: Perform to endpoint /api/cargo
    ResultActions response = mockMvc.perform(get("/api/cargo"));

    //Then: Verify if return status is 200 OK, and return the list of DTO
    response.andExpect(status().is(200))
      .andDo(print())
      .andExpect(jsonPath("$.size()", CoreMatchers.is(listaCargoPersisted.size() - 1)));
  }

  @Test
  public void givenCargo_whenFindByIdCargo_thenReturnFoundCargoDto() throws Exception {
    //Given: Create and persist Cargo in DB
    Cargo cargoPersisted = cargoService.createNew(Cargo.builder().nombreCargo("Administrador").descripcion("Test1").build());

    //When: GET request in /api/cargo/{idCargo}
    ResultActions response = mockMvc.perform(get("/api/cargo/{idCargo}", cargoPersisted.getIdCargo()));

    //Then: Verify if return status is 200 OK and return the DTO of Cargo inserted in DB
    response.andExpect(status().is(200));
    response.andExpect(jsonPath("$.idCargo", CoreMatchers.is(cargoPersisted.getIdCargo())));
    response.andExpect(jsonPath("$.nombreCargo", CoreMatchers.is("Administrador")));
    response.andExpect(jsonPath("$.descripcion", CoreMatchers.is("Test1")));
    response.andDo(print());
  }

  @Test
  public void givenCargoDto_whenCreateCargo_thenReturnPersistedCargoDto() throws Exception {
    //Given: Cargo submitted by user input
    CargoDto cargoDto = CargoDto.builder().nombreCargo("Administrador").descripcion("Test1").build();
    String cargoDtoJson = objectMapper.writeValueAsString(cargoDto);
    log.info("Logging cargoDto: {}", cargoDto);
    log.info("Logging cargoDtoJson: {}", cargoDtoJson);

    //When: POST request in /api/cargo and the body is inserted in DB
    ResultActions response = mockMvc.perform(
      post("/api/cargo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(cargoDtoJson)
        .accept(MediaType.APPLICATION_JSON_VALUE)
    );

    //Then: read and verify the response
    JsonNode responseBodyAsJson = objectMapper.readTree(response.andReturn().getResponse().getContentAsString());
    Integer idCargo = responseBodyAsJson.get("idCargo").asInt();

    response.andExpect(status().is(201));
    response.andExpect(header().string("Location", CoreMatchers.containsString("/api/cargo/" + idCargo)));
    response.andExpect(jsonPath("$.idCargo", CoreMatchers.is(idCargo)));
    response.andExpect(jsonPath("$.nombreCargo", CoreMatchers.is("Administrador")));
    response.andExpect(jsonPath("$.descripcion", CoreMatchers.is("Test1")));
    response.andDo(print());
  }

  @Test
  public void givenCargo_whenUpdateCargo_thenReturnUpdatedCargoDto() throws Exception {
    //Given: create a Cargo and persist in DB, then change cargoCreated values
    Cargo cargoCreated = cargoService.createNew(Cargo.builder().nombreCargo("Administrador").descripcion("Test1").build());
    LocalDateTime fechaRegistroRetrieved = cargoService.findById(cargoCreated.getIdCargo()).getFechaRegistro();

    CargoDto cargoDto = modelMapper.map(cargoCreated, CargoDto.class);
    cargoDto.setNombreCargo("Administrador Updated");
    cargoDto.setDescripcion("Test1 Updated");
    cargoDto.setFechaRegistro(LocalDateTime.of(2002, Month.FEBRUARY, 11, 6, 30));

    String cargoDtoJson = objectMapper.writeValueAsString(cargoDto);

    //When: PUT request in /api/cargo
    ResultActions response = mockMvc.perform(put("/api/cargo")
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .content(cargoDtoJson)
      .contentType(MediaType.APPLICATION_JSON)
    );

    //Then: read, compare and verify the response
    response.andExpect(status().is(200))
      .andExpect(jsonPath("$.idCargo", CoreMatchers.is(cargoCreated.getIdCargo())))
      .andExpect(jsonPath("$.nombreCargo", CoreMatchers.is("Administrador Updated")))
      .andExpect(jsonPath("$.descripcion", CoreMatchers.is("Test1 Updated")))
      .andExpect(jsonPath("$.fechaRegistro", CoreMatchers.is(fechaRegistroRetrieved.toString())))
      .andDo(print());
  }

  @Test
  public void givenCargo_whenDeleteCargo_thenReturnEmptyContent() throws Exception {
    //Given: create a Cargo and persist in DB
    Cargo cargoCreated = cargoService.createNew(Cargo.builder().nombreCargo("Administrador").descripcion("Test1").build());

    //When DELETE request in /api/cargo/{idCargo}
    ResultActions response = mockMvc.perform(delete("/api/cargo/{idCargo}", cargoCreated.getIdCargo()));

    //Then: read, compare and verify the response
    Cargo cargoFound = cargoService.findById(cargoCreated.getIdCargo());
    Assert.isNull(cargoFound, "Cargo not deleted");
    response.andExpect(status().is(204))
      .andExpect(content().string(""))
      .andDo(print());
  }
}
