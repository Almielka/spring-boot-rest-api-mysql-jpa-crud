package com.almielka.restapicrud.controller;

import com.almielka.restapicrud.domain.Company;
import com.almielka.restapicrud.domain.Order;
import com.almielka.restapicrud.domain.Product;
import com.almielka.restapicrud.service.OrderService;
import com.almielka.restapicrud.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Anna S. Almielka
 */

@RestController
@RequestMapping(value = "/api/products/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private ProductService productService;
    private OrderService orderService;

    public OrderController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    //create Order
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@RequestBody List<Product> productList) {
        List<Product> orderedProductList = productService.findAll()
                .stream().filter(productList::contains).collect(Collectors.toList());
        if (orderedProductList.size() > 0) {
            Order newOrder = new Order();
            newOrder.setCreatedAt(Instant.now());
            newOrder.setProductList(orderedProductList);
            orderService.saveAndFlush(newOrder);
            createProductWithOrderList(newOrder);
            return ResponseEntity.status((HttpStatus.CREATED)).body(newOrder);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private void createProductWithOrderList(Order newOrder) {
        newOrder.getProductList().forEach(item -> {
            item.getOrderList().add(newOrder);
            productService.saveAndFlush(item);
        });
    }

    //get the order History
    @GetMapping("/history")
    public ResponseEntity<List<Order>> findAllOrders() {
        List<Order> orderList = orderService.findAll();
        if (orderList.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(orderList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //get a list of the most ordered products
    @GetMapping("/history-of-the-most-ordered-products")
    public ResponseEntity<Set<Product>> findMostOrderedProducts() {
        Set<Product> productSet = productService.findAllOrderedProducts();
        Set<Product> orderedProductSet = getMostOrderedProductSet(productSet);
        if (orderedProductSet.size() > 0) {
            return ResponseEntity.status((HttpStatus.OK)).body(orderedProductSet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //get a list of the most ordered companies
    @GetMapping("/history-of-the-most-ordered-companies")
    public ResponseEntity<Set<Company>> findMostOrderedCompanies() {
        Set<Product> productSet = productService.findAllOrderedProducts();
        Set<Company> orderedCompanySet = getMostOrderedProductSet(productSet)
                .stream()
                .map(Product::getCompany)
                .collect(Collectors.toSet());
        if (orderedCompanySet.size() > 0) {
            return ResponseEntity.status((HttpStatus.OK)).body(orderedCompanySet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private Set<Product> getMostOrderedProductSet(Set<Product> productSet) {
        int max = new ArrayList<>(productSet).get(0).getOrderList().size();
        return productSet.stream()
                .filter(item -> item.getOrderList().size() == max).collect(Collectors.toSet());
    }

}
