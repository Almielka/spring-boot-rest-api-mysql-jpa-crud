package com.almielka.restapicrud.service.impl;

import com.almielka.restapicrud.domain.Product;
import com.almielka.restapicrud.repository.ProductRepository;
import com.almielka.restapicrud.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Extension of {@link CommonServiceImpl}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link CommonServiceImpl#CommonServiceImpl}
 * class {@code Product}, repository {@code ProductRepository}
 *
 * @author Anna S. Almielka
 */

@Service
public class ProductServiceImpl extends CommonServiceImpl<Product, ProductRepository> implements ProductService {
    public ProductServiceImpl(ProductRepository repository) {
        super(repository);
    }

    @Override
    public Set<Product> findByCompanyNameContaining(String name) {
//        return repository.findAll().stream()
//                .filter(item -> item.getCompany().getName().contains(name))
//                .collect(Collectors.toSet());
        return repository.findByCompanyNameContaining(name);
    }

    @Override
    public Set<Product> findByCompanyType(String type) {
//        return repository.findAll().stream()
//                .filter(item -> item.getCompany().getCompanyType().toString().equals(type.toUpperCase()))
//                .collect(Collectors.toSet());
        return repository.findByCompanyType(type.toUpperCase());
    }

    @Override
    public Set<Product> findBeforeDate(String date) {
        return repository.findAll().stream()
                .filter(item -> item.getCreatedDate().isBefore(Instant.parse(date)))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Product> findAfterDate(String date) {
        return repository.findAll().stream()
                .filter(item -> item.getCreatedDate().isAfter(Instant.parse(date)))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Product> findBetweenDates(String afterDate, String beforeDate) {
        return repository.findAll().stream()
                .filter(item -> item.getCreatedDate().isAfter(Instant.parse(afterDate)))
                .filter(item -> item.getCreatedDate().isBefore(Instant.parse(beforeDate)))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Product> findAllOrderedProducts() {
        return repository.findAllOrderedProducts();
    }

    @Override
    public Set<Product> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

}