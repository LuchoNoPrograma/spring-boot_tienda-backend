package com.tiendadbii.tiendadbii.model.entity.parent;

import com.tiendadbii.tiendadbii.model.Estado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditoriaRevision {
  //Anotacion que convierte Enum en STRING
  @Enumerated(EnumType.STRING)
  @Column(name = "_estado", length = 55)
  private Estado estado;


  @Column(name = "_registro")
  private LocalDateTime fechaRegistro;
}