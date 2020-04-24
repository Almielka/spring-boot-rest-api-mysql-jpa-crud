package com.almielka.springbootrestapimysqljpacrudexample.service.impl;

import com.almielka.springbootrestapimysqljpacrudexample.domain.AbstractIdEntity;
import com.almielka.springbootrestapimysqljpacrudexample.repository.CommonRepository;
import com.almielka.springbootrestapimysqljpacrudexample.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link CommonService}
 * includes parameterized constructor for the future repository
 * and with override all methods
 *
 * @param <T> Class extends {@link AbstractIdEntity}
 * @param <R> Repository extends {@link CommonRepository <T>}
 *
 * @author Anna S. Almielka
 */

public abstract class CommonServiceImpl<T extends AbstractIdEntity, R extends CommonRepository<T>> implements CommonService<T> {

    protected final R repository;

    @Autowired
    public CommonServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<T> findAllById(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }
}