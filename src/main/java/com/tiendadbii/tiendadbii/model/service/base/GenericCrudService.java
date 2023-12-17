package com.tiendadbii.tiendadbii.model.service.base;

import java.util.List;
import java.util.Optional;

public interface GenericCrudService<T, K> {
  List<T> findAll();

  T save(T entity);

  Optional<T> findById(K id);

  void deleteById(K id);
}
