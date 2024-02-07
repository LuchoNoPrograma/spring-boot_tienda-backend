package com.tiendadbii.tiendadbii.model.service.impl;

import com.tiendadbii.tiendadbii.model.entity.Cargo;
import com.tiendadbii.tiendadbii.model.repository.CargoRepository;
import com.tiendadbii.tiendadbii.model.service.interfaces.ICargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements ICargoService {
  private final CargoRepository cargoRepository;

  @Override
  public List<Cargo> findAll() {
    return cargoRepository.findAll(Sort.by("idCargo").descending());
  }

  @Override
  public Cargo createNew(Cargo entity) {
    if (entity.getIdCargo() == null || entity.getIdCargo() == 0) {
      //entity.setFechaRegistro is performed by AuditoryConfig
      return cargoRepository.save(entity);
    }
    throw new IllegalArgumentException("Id must be null or 0");
  }

  @Override
  public Cargo update(Cargo entity) {
    if (entity.getIdCargo() == null) throw new IllegalArgumentException("Id must not be null");

    cargoRepository.findById(entity.getIdCargo()).ifPresent(old -> {
      entity.setFechaRegistro(old.getFechaRegistro());
    });

    return cargoRepository.save(entity);
  }

  @Override
  public void deleteById(Integer id) {
    cargoRepository.deleteById(id);
  }

  @Override
  public Cargo findById(Integer id) {
    return cargoRepository.findById(id).orElse(null);
  }

}
