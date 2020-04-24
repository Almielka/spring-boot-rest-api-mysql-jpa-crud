package com.almielka.springbootrestapimysqljpacrudexample.repository;

import com.almielka.springbootrestapimysqljpacrudexample.domain.Order;

/**
 * Extension of {@link CommonRepository} with concrete generic Order
 * includes additional methods only for Order
 *
 * @author Anna S. Almielka
 */

public interface OrderRepository extends CommonRepository<Order> {
}
