package com.tiendadbii.tiendadbii.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tiendadbii.tiendadbii.dto.EmpleadoDto;
import com.tiendadbii.tiendadbii.dto.HorarioDto;
import com.tiendadbii.tiendadbii.dto.OcupaDto;
import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.entity.Empleado;
import com.tiendadbii.tiendadbii.model.entity.Horario;
import com.tiendadbii.tiendadbii.model.entity.Ocupa;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICargoService;
import com.tiendadbii.tiendadbii.model.service.interfaces.IEmpleadoService;
import com.tiendadbii.tiendadbii.util.swagger_example.EmpleadoExample;
import com.tiendadbii.tiendadbii.views.EmpleadoViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin({"*"})
@RequestMapping("/api/empleado")
@Tag(name = "5. Empleado", description = "Con este EndPoint se puede realizar todas las operaciones de Empleado.")
public class EmpleadoApi {
  private final ModelMapper modelMapper;
  private final IEmpleadoService empleadoService;
  private final ICargoService cargoService;

  @Operation(summary = "Listar todos los empleados", description = "Retorna la lista de todos los empleados sin aplicar ningún filtro.")
  @JsonView({EmpleadoViews.Ver.class})
  @GetMapping
  public ResponseEntity<List<EmpleadoDto>> findAll() {
    return ResponseEntity.ok().body(empleadoService.findAll().stream().map(this::toDto).toList());
  }

  @Operation(summary = "Lista todos los empleados en formato de página", description = "Retorna la lista de empleados, basandose en 4 parametros.")
  @JsonView({EmpleadoViews.Ver.class})
  @GetMapping("/pageable")
  public ResponseEntity<List<EmpleadoDto>> findAllPageable(
    @RequestParam(required = false, defaultValue = "0") int page,
    @RequestParam(required = false, defaultValue = "10") int size,
    @RequestParam(required = false, defaultValue = "idEmpleado") String sort,
    @RequestParam(required = false, defaultValue = "ASC") String order) {

    Sort.Direction direction = Sort.Direction.fromString(order);
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

    return ResponseEntity.ok().body(empleadoService.findAll(pageable).stream().map(this::toDto).toList());
  }

  @Operation(summary = "Buscar un empleado dado {idEmpleado}", description = "Retorna un empleado con todos sus campos.",
    responses = {
      @ApiResponse(responseCode = "200", description = "Empleado encontrado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Empleado no encontrada con el idEmpleado: {idEmpleados}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
    })
  @GetMapping("/{idEmpleado}")
  @JsonView({EmpleadoViews.Ver.class})
  public ResponseEntity<EmpleadoDto> findById(@PathVariable Integer idEmpleado) {
    if (empleadoService.findById(idEmpleado) == null) {
      throw new EntityNotFoundException("Empleado no encontrada con el idEmpleado: " + idEmpleado);
    }
    return ResponseEntity.ok().body(this.toDto(empleadoService.findById(idEmpleado)));
  }

  @Operation(summary = "Crear nuevo Empleado",
    description = """
      Para crear un nuevo Empleado, debe proporcionar un valor a todos los campos establecidos en el ejemplo. Debe considerar lo siguiente:
            
      1) El campo "listaOcupa" hace referencia a los cargos que ocupa el empleado, por lo que en el campo "idCargo" debe estar asociado a un Cargo en la base de datos.
      2) El campo "listaHorario" hace referencia a los horarios que labora el empleado.
      """,
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "",
      content = @Content(
        examples = {@ExampleObject(
          summary = "Empleado nuevo",
          name = "Ejemplo del cuerpo de la petición para registrar un nuevo empleado."
        )}
      )
    ),
    responses = {
      @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
      @ApiResponse(responseCode = "400", description = "Error en la Request, asegurese de completar todos los campos"),
      @ApiResponse(responseCode = "404", description = "Cargo no encontrado con el idCargo: {idCargo}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
    }
  )
  @JsonView({EmpleadoViews.Ver.class})
  @PostMapping
  public ResponseEntity<EmpleadoDto> createEmpleado(@RequestBody @JsonView({EmpleadoViews.Crear.class}) EmpleadoDto dto) {
    List<Cargo> listaCargo = dto.getListaOcupa().stream().map(ocupa -> {
      Cargo cargo = cargoService.findById(ocupa.getIdCargo());
      if (cargo == null) {
        throw new EntityNotFoundException("Cargo no encontrado con el idCargo: " + ocupa.getIdCargo());
      }
      return cargo;
    }).toList();

    Empleado empleado = this.toEntity(dto);
    empleado.getListaHorario().forEach(horario -> horario.setEmpleado(empleado));
    empleado.getListaOcupa().forEach(ocupa -> ocupa.setEmpleado(empleado));

    EmpleadoDto empleadoDto = this.toDto(empleadoService.createNew(empleado, listaCargo));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .header("Location", "/api/empleado/" + empleadoDto.getIdEmpleado())
      .body(empleadoDto);
  }

  @Operation(summary = "Actualizar Empleado",
    description = """
      Para actualizar un Empleado, debe proporcionar un valor a todos los campos establecidos en el ejemplo. Debe considerar lo siguiente:
        1) El Endpoint dispone de 4 ejemplos para poder realizar actualizaciónes de datos del empleado.
        2) Cualquier campo que no se encuentre en el ejemplo, no se tomara en cuenta ni actualizara.
      """,
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "",
      content = @Content(
        examples = {
          @ExampleObject(
            summary = "Actualizar datos personales del empleado",
            name = "Empleado simple",
            value = EmpleadoExample.EMPLEADO_ACTUALIZAR_SIN_SOBREESCRIBIR_NADA,
            description = """
              Si se requiere actualizar solo los datos personales del empleado, se debe omitir el campo
              <b>"listaOcupa"</b> y el campo <b>"listaHorario"</b> con el fin de no sobreescribir los cargos(ocupa) y horarios asignados al empleado.
              """
          ),
          @ExampleObject(
            summary = "Actualizar datos personales del empleado y sus horarios laborales",
            name = "Empleado con horarios",
            value = EmpleadoExample.EMPLEADO_ACTUALIZAR_SOBREESCRIBIR_LISTA_HORARIO,
            description = """
              Si se requiere actualizar los datos personales del empleado y sus horarios laborales, se debe completar el campo <b>"listaHorario"</b>,
              y asignarles los valores correspondientes a cada item de la lista. Este cambio sobreescribira los horarios laborales del empleado.
              """
          ),
          @ExampleObject(
            summary = "Actualizar datos personales del empleado y sus cargos laborales",
            name = "Empleado con cargos",
            value = EmpleadoExample.EMPLEADO_ACTUALIZAR_SOBREESCRIBIR_LISTA_OCUPA,
            description = """
              Si se requiere actualizar los datos personales del empleado y sus cargos laborales, se debe completar el campo <b>"listaOcupa"</b>,
              y asignarles los valores correspondientes a cada item de la lista. Este cambio sobreescribira los cargos laborales del empleado.
              """
          ),
          @ExampleObject(
            summary = "Actualizar datos personales del empleado, sus horarios laborales y sus cargos laborales",
            name = "Empleado con horarios y cargos",
            value = EmpleadoExample.EMPLEADO_ACTUALIZAR_SOBREESCRIBIR_LISTA_HORARIO_Y_OCUPA,
            description = """
              Si se requiere actualizar los datos personales del empleado, sus horarios y cargos laborales, se debe completar el campo <b>"listaHorario"</b>,
              y el campo <b>"listaOcupa"</b> para luego asignarles los valores correspondientes a cada item de las listas.
              Este cambio sobreescribira los horarios y cargos laborales del empleado.
              """
          )

        }
      )
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
      @ApiResponse(responseCode = "400", description = "Error en la Request, asegurese de completar todos los campos de manera correcta", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(responseCode = "404", description = "Empleado no encontrado con el idEmpleado: {idEmpleado} " +
        "| Cargo no encontrado con el idCargo: {idCargo}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
    }
  )
  @JsonView({EmpleadoViews.Ver.class})
  @PutMapping
  public ResponseEntity<EmpleadoDto> updateEmpleado(@RequestBody @JsonView({EmpleadoViews.Actualizar.class}) EmpleadoDto dto) {
    if (empleadoService.findById(dto.getIdEmpleado()) == null) {
      throw new EntityNotFoundException("Empleado no encontrado con el idEmpleado: " + dto.getIdEmpleado());
    }

    List<Cargo> listaCargo = new ArrayList<>();
    if (dto.getListaOcupa() != null && !dto.getListaOcupa().isEmpty()) {
      listaCargo = dto.getListaOcupa().stream().map(ocupa -> {
        Cargo cargo = cargoService.findById(ocupa.getIdCargo());
        if (cargo == null) {
          throw new EntityNotFoundException("Cargo no encontrado con el idCargo: " + ocupa.getIdCargo());
        }
        return cargo;
      }).toList();
    }

    return ResponseEntity.ok(this.toDto(empleadoService.update(this.toEntity(dto), listaCargo)));
  }

  @Operation(summary = "Eliminar Empleado", description = "Eliminara todo el registro del empleado, incluyendo la lista de horarios y cargos asociados",
    responses = {
      @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Empleado no encontrado con el id: {idEmpleado}", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor, mas detalles en campo \"message\"", content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))
    }
  )
  @DeleteMapping("/{idEmpleado}")
  public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer idEmpleado) {
    if (empleadoService.findById(idEmpleado) == null) {
      throw new EntityNotFoundException("Empleado no encontrado con el id: " + idEmpleado);
    }
    empleadoService.deleteById(idEmpleado);
    return ResponseEntity.noContent().build();
  }

  private Empleado toEntity(EmpleadoDto dto) {
    Empleado empleado = modelMapper.map(dto, Empleado.class);
    if (dto.getListaHorario() != null) {
      empleado.setListaHorario(dto.getListaHorario().stream().map(horario -> modelMapper.map(horario, Horario.class)).toList());
    }
    if (dto.getListaOcupa() != null) {
      empleado.setListaOcupa(dto.getListaOcupa().stream().map(ocupa -> modelMapper.map(ocupa, Ocupa.class)).toList());
    }

    return empleado;
  }

  private EmpleadoDto toDto(Empleado entity) {
    EmpleadoDto empleadoDto = modelMapper.map(entity, EmpleadoDto.class);
    if (entity.getListaHorario() != null) {
      empleadoDto.setListaHorario(entity.getListaHorario().stream().map(horario -> modelMapper.map(horario, HorarioDto.class)).toList());
    }
    if (entity.getListaOcupa() != null) {
      empleadoDto.setListaOcupa(entity.getListaOcupa().stream().map(ocupa -> modelMapper.map(ocupa, OcupaDto.class)).toList());
    }
    return empleadoDto;
  }
}
