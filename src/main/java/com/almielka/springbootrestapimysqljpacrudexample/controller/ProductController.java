package com.almielka.springbootrestapimysqljpacrudexample.controller;


import com.almielka.springbootrestapimysqljpacrudexample.domain.Company;
import com.almielka.springbootrestapimysqljpacrudexample.domain.Product;
import com.almielka.springbootrestapimysqljpacrudexample.service.CompanyService;
import com.almielka.springbootrestapimysqljpacrudexample.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * @author Anna S. Almielka
 */

@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private ProductService productService;
    private CompanyService companyService;


    public ProductController(ProductService productService, CompanyService companyService) {
        this.productService = productService;
        this.companyService = companyService;
    }

    //add Product
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        Optional<Company> optionalCompany = companyService.findByName(newProduct.getCompany().getName());
        if (optionalCompany.isPresent()) {
            newProduct.getCompany().setId(optionalCompany.get().getId());
            productService.saveAndFlush(newProduct);
            return ResponseEntity.status((HttpStatus.CREATED)).body(newProduct);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //read Product by Id
    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<Product> readProductById(@PathVariable("id") Long id) {
        Optional<Product> optionalProduct = productService.findById(id);
        return optionalProduct.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //search by Product name
    @GetMapping("/search-by-name/{name}")
    public ResponseEntity<Set<Product>> getProductByName(@PathVariable("name") String name) {
        Set<Product> productSet = productService.findByNameContaining(name);
        return getProductSetResponseEntity(productSet);
    }

    //search by Company name
    @GetMapping("/search-by-company-name/{name}")
    public ResponseEntity<Set<Product>> getProductByCompanyName(@PathVariable("name") String name) {
        Set<Product> productSet = productService.findByCompanyNameContaining(name);
        return getProductSetResponseEntity(productSet);
    }

    //search by Company type
    @GetMapping("/search-by-company-type/{type}")
    public ResponseEntity<Set<Product>> getProductByCompanyType(@PathVariable("type") String type) {
        Set<Product> productSet = productService.findByCompanyType(type);
        return getProductSetResponseEntity(productSet);
    }

    //search before date
    @GetMapping("/search-before-date")
    public ResponseEntity<Set<Product>> getProductBeforeDate(@RequestParam("date") String date) {
        Set<Product> productSet = productService.findBeforeDate(date);
        return getProductSetResponseEntity(productSet);
    }

    //search after date
    @GetMapping("/search-after-date")
    public ResponseEntity<Set<Product>> getProductAfterDate(@RequestParam("date") String date) {
        Set<Product> productSet = productService.findAfterDate(date);
        return getProductSetResponseEntity(productSet);
    }

    //search between dates
    @GetMapping("/search-between-dates")
    public ResponseEntity<Set<Product>> getProductBetweenDates(
            @RequestParam("afterDate") String afterDate, @RequestParam("beforeDate") String beforeDate) {
        Set<Product> productSet = productService.findBetweenDates(afterDate, beforeDate);
        return getProductSetResponseEntity(productSet);
    }

    private ResponseEntity<Set<Product>> getProductSetResponseEntity(Set<Product> productSet) {
        if (productSet.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(productSet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //delete Product by Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
