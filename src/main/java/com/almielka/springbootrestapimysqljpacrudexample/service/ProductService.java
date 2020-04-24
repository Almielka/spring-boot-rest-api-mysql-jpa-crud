package com.almielka.springbootrestapimysqljpacrudexample.service;

import com.almielka.springbootrestapimysqljpacrudexample.domain.Product;

import java.util.Set;

/**
 * ProductService interface includes additional methods only for Product
 *
 * @author Anna S. Almielka
 */

public interface ProductService extends CommonService<Product> {
    Set<Product> findByCompanyNameContaining(String name);
    Set<Product> findByCompanyType(String name);
    Set<Product> findBeforeDate(String date);
    Set<Product> findAfterDate(String date);
    Set<Product> findBetweenDates (String startDate, String endDate);
    Set<Product> findAllOrderedProducts();
    Set<Product> findByNameContaining(String name);

}
