package com.almielka.restapicrud.repository;

import com.almielka.restapicrud.domain.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * Extension of {@link CommonRepository} with concrete generic Company
 * includes additional methods only for Company
 *
 * @author Anna S. Almielka
 */

@Repository
public interface CompanyRepository extends CommonRepository<Company> {

    /**
     * Retrieve {@link Company}s from the data store by {@code name}, returning all companies
     * whose name <i>containing</i> with the given name.
     *
     * @param name Value to search for
     * @return a Collection of matching companies (or an empty Collection if none found)
     */
    @Query("SELECT m FROM Company m WHERE m.name LIKE %:name%")
    @Transactional(readOnly = true)
    Set<Company> findByNameContaining(@Param("name") String name);

    /**
     * Retrieve {@link Company} from the data store by {@code name}, returning company
     * whose name <i>equals</i> with the given name.
     *
     * @param name Value to search for
     * @return an {@code Optional}
     */
    @Query("SELECT m FROM Company m WHERE m.name=:name")
    @Transactional(readOnly = true)
    Optional<Company> findByName(@Param("name") String name);
}
