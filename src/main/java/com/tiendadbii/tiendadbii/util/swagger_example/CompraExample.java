package com.tiendadbii.tiendadbii.util.swagger_example;

public class CompraExample {
  public static final String COMPRA_PARA_REGISTRAR_NUEVO_PRODUCTO = """
    {
      "listaDetalleCompra": [
        {
          "producto": {
            "codigoBarra": "string",
            "nombreProducto": "string",
            "descripcion": "string",
            "precioProducto": 0,
            "precioVenta": 0,
            "rutaArchivo": "string"
          },
          "fechaDetalle": "2024-02-04T16:34:04.742Z",
          "cantidad": 0,
          "subtotalDetalle": 0,
          "subtotalDescDetalle": 0
        }
      ],
      "fechaCompra": "2024-02-04T16:34:04.742Z"
    }
    """;
  public static final String COMPRA_PARA_INCREMENTAR_STOCK_DE_PRODUCTO_REGISTRADO = """
    {
      "listaDetalleCompra": [
        {
          "producto": {
            "codigoProducto": 0
          },
          "fechaDetalle": "2024-02-04T16:34:04.742Z",
          "cantidad": 0,
          "subtotalDetalle": 0,
          "subtotalDescDetalle": 0
        }
      ],
      "fechaCompra": "2024-02-04T16:34:04.742Z"
    }
    """;
  public static final String COMPRA_CREADA_EXITOSAMENTE = """
    {
      "proveedor": {
        "idProveedor": 0,
      },
      "listaDetalleCompra": [
        {
          "producto": {
            "codigoProducto": 0,
            "codigoBarra": "string",
            "nombreProducto": "string",
            "descripcion": "string",
            "precioProducto": 0,
            "precioVenta": 0,
            "stock": 0,
            "rutaArchivo": "string"
          },
          "idDetalleCompra": 0,
          "fechaDetalle": "2024-02-04T16:34:04.742Z",
          "cantidad": 0,
          "subtotalDetalle": 0,
          "subtotalDescDetalle": 0
        }
      ],
      "idCompra": 0,
      "fechaCompra": "2024-02-04T16:34:04.742Z",
      "totalCompra": 0,
      "totalDescCompra": 0
    }
    """;
  public static final String COMPRA_CON_PROVEEDOR_ID = """
    {
      "proveedor": {
        "idProveedor": 0
      },
      "listaDetalleCompra": [
        {
          "producto": {
            "codigoProducto": 0,
            "codigoBarra": "string",
            "nombreProducto": "string",
            "descripcion": "string",
            "precioProducto": 0,
            "precioVenta": 0,
            "stock": 0,
            "rutaArchivo": "string"
          },
          "idDetalleCompra": 0,
          "fechaDetalle": "2024-02-04T16:34:04.742",
          "cantidad": 0,
          "subtotalDetalle": 0,
          "subtotalDescDetalle": 0
        }
      ],
      "idCompra": 0,
      "fechaCompra": "2024-02-04T16:34:04.742",
      "totalCompra": 0,
      "totalDescCompra": 0
    }
    """;
}
