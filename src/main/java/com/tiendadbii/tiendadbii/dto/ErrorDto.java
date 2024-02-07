package com.tiendadbii.tiendadbii.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;


@Data
@Builder
public class ErrorDto {
  ZonedDateTime timestamp;
  Integer status;
  String error;
  String trace;
  String message;
  String path;

}
