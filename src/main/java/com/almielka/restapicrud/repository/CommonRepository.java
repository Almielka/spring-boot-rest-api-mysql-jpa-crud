package com.almielka.restapicrud.repository;

import com.almielka.restapicrud.domain.AbstractIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * Generic Repository interface with common CRUD methods
 * @param <T> the entity class related to this Repository
 *
 * @author Anna S. Almielka
 */

@NoRepositoryBean
public interface CommonRepository<T extends AbstractIdEntity> extends JpaRepository<T, Long> {
    <S extends T> S saveAndFlush(S entity);
    Optional<T> findById(Long id);
    List<T> findAll();
    List<T> findAllById(Iterable<Long> ids);
    void deleteById(Long id);
}
