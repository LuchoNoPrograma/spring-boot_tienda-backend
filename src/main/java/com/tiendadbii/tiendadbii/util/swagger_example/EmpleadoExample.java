package com.tiendadbii.tiendadbii.util.swagger_example;

public class EmpleadoExample {
  public static final String EMPLEADO_ACTUALIZAR_SIN_SOBREESCRIBIR_NADA = """
    {
      "ci": "string",
      "nombres": "string",
      "apellidos": "string",
      "direccion": "string",
      "celular": "string",
      "prefijoCelular": "string",
      "idEmpleado": 0,
      "email": "string"
    }
    """;

  public static final String EMPLEADO_ACTUALIZAR_SOBREESCRIBIR_LISTA_HORARIO = """
    {
      "ci": "string",
      "nombres": "string",
      "apellidos": "string",
      "direccion": "string",
      "celular": "string",
      "prefijoCelular": "string",
      "idEmpleado": 0,
      "email": "string",
      "listaHorario": [
        {
          "dia": "LUNES",
          "horaIngreso": "HH:mm:SS",
          "horaSalida": "HH:mm:SS"
        } ]
    }
     
    """;

  public static final String EMPLEADO_ACTUALIZAR_SOBREESCRIBIR_LISTA_OCUPA = """
    {
      "ci": "string",
      "nombres": "string",
      "apellidos": "string",
      "direccion": "string",
      "celular": "string",
      "prefijoCelular": "string",
      "idEmpleado": 0,
      "email": "string",
      "listaOcupa": [
        {
          "idCargo": 0,
          "fechaInicio": "2024-02-06",
          "fechaFin": "2024-02-06"
        }
      ]
    }
    """;

  public static final String EMPLEADO_ACTUALIZAR_SOBREESCRIBIR_LISTA_HORARIO_Y_OCUPA = """
    {
      "ci": "string",
      "nombres": "string",
      "apellidos": "string",
      "direccion": "string",
      "celular": "string",
      "prefijoCelular": "string",
      "idEmpleado": 0,
      "email": "string",
      "listaHorario": [
        {
          "dia": "LUNES",
          "horaIngreso": "HH:mm:SS",
          "horaSalida": "HH:mm:SS"
        }
      ],
      "listaOcupa": [
        {
          "idCargo": 0,
          "fechaInicio": "2024-02-06",
          "fechaFin": "2024-02-06"
        }
      ]
    }
    """;
}
