package com.tiendadbii.tiendadbii.model.entity.parent;

import com.tiendadbii.tiendadbii.model.Estado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString
public abstract class AuditoriaRevision {
  //Anotacion que convierte Enum en STRING
  @Enumerated(EnumType.STRING)
  @Column(name = "_estado", length = 55)
  private Estado estado;


  @Column(name = "_registro")
  @CreatedDate
  private LocalDateTime fechaRegistro;
}