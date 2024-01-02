package com.tiendadbii.tiendadbii.model.service.base;

import java.util.List;
import java.util.Optional;

public interface GenericCrudService<T, K> {
  List<T> findAll();
  T createNew(T entity);
  T update(T entity);
  void deleteById(K id);
  T findById(K id);
}
