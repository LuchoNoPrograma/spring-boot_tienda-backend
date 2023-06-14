package com.tiendadbii.tiendadbii.model.entity;

import com.tiendadbii.tiendadbii.model.Estado;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditoriaRevision {
  //Anotacion que convierte Enum en STRING
  @Enumerated(EnumType.STRING)
  @Column(name = "_estado", length = 55)
  private Estado estado;
}