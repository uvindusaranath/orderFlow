package org.orderflow.infrastructure.persistence;

import org.orderflow.application.port.OrderRepository;
import org.orderflow.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class InMemoryOrderRepository implements OrderRepository {


    private final List<Order> orders = new ArrayList<>();
    @Override
    public Order save(Order order) {
      orders.add(order);
      return order;
    }

    @Override
    public List<Order> findAll() {
        return List.copyOf(orders);
    }
}
