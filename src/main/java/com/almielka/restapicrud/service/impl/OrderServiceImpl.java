package com.almielka.restapicrud.service.impl;

import com.almielka.restapicrud.domain.Order;
import com.almielka.restapicrud.repository.OrderRepository;
import com.almielka.restapicrud.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * Extension of {@link CommonServiceImpl}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link CommonServiceImpl#CommonServiceImpl}
 * class {@code Order}, repository {@code OrderRepository}
 *
 * @author Anna S. Almielka
 */

@Service
public class OrderServiceImpl extends CommonServiceImpl<Order, OrderRepository> implements OrderService {
    public OrderServiceImpl(OrderRepository repository) {
        super(repository);
    }

}