package org.orderflow.application.port;

import org.orderflow.domain.Order;

import java.util.List;

public interface OrderRepository {

    Order save(Order order);

    //Why return the Order again?
    //Because a persistence implementation might eventually enrich it with system-controlled data such as:
    // id & createdAt
    // returning the entity is a common and useful contract shape.

    List<Order> findAll();

}
