package com.almielka.restapicrud.repository;

import com.almielka.restapicrud.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.almielka.restapicrud.domain.Company;

import java.util.Set;

/**
 * Extension of {@link CommonRepository} with concrete generic Product
 * includes additional methods only for Product
 *
 * @author Anna S. Almielka
 */

@Repository
public interface ProductRepository extends CommonRepository<Product> {

    /**
     * Retrieve {@link Product}s from the data store by {@code name} of {@link Company}, returning all products
     * whose Company name <i>containing</i> with the given name.
     *
     * @param name Value to search for
     * @return a Collection of matching products (or an empty Collection if none found)
     */
    @Query(value = "SELECT * " +
            "FROM product p " +
            "INNER JOIN company c " +
            "ON p.company_id = c.id " +
            "WHERE c.name LIKE %:name%",
            nativeQuery = true)
    @Transactional(readOnly = true)
    Set<Product> findByCompanyNameContaining(@Param("name") String name);

    /**
     * Retrieve {@link Product}s from the data store by {@code name} of {@link Company}, returning all products
     * whose Company type <i>equals</i> with the given type.
     *
     * @param type Value to search for
     * @return a Collection of matching products (or an empty Collection if none found)
     */
    @Query(value = "SELECT * " +
            "FROM product p " +
            "INNER JOIN company c " +
            "ON p.company_id = c.id " +
            "WHERE c.company_type = :type",
            nativeQuery = true)
    @Transactional(readOnly = true)
    Set<Product> findByCompanyType(@Param("type") String type);

    @Query(value = "SELECT p, " +
            "COUNT(*)" +
            "FROM Product p " +
            "INNER JOIN p.orderList ol " +
            "GROUP BY p.id " +
            "ORDER BY count(p.id) DESC"
            //"ORDER BY count(p.id) DESC LIMIT 1",
            //nativeQuery = true
    )
    @Transactional(readOnly = true)
    Set<Product> findAllOrderedProducts();

    /**
     * Retrieve {@link Product}s from the data store by {@code name}, returning all products
     * whose name <i>containing</i> with the given name.
     *
     * @param name Value to search for
     * @return a Collection of matching products (or an empty Collection if none found)
     */
    @Query("SELECT m FROM Product m WHERE m.name LIKE %:name%")
    @Transactional(readOnly = true)
    Set<Product> findByNameContaining(@Param("name") String name);
}
