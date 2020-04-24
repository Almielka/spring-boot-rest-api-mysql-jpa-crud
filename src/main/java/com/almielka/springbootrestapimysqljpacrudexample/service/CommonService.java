package com.almielka.springbootrestapimysqljpacrudexample.service;

import com.almielka.springbootrestapimysqljpacrudexample.domain.AbstractIdEntity;

import java.util.List;
import java.util.Optional;

/**
 * Generic Service interface with common CRUD methods
 *
 * @author Anna S. Almielka
 */

public interface CommonService<T extends AbstractIdEntity> {
    <S extends T> S saveAndFlush(S entity);
    Optional<T> findById(Long id);
    List<T> findAll();
    List<T> findAllById(Iterable<Long> ids);
    void deleteById(Long id);
}