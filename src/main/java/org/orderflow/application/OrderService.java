package org.orderflow.application;

import org.orderflow.application.port.InventoryClient;
import org.orderflow.application.port.OrderRepository;
import org.orderflow.domain.Order;

public class OrderService {
    private final InventoryClient inventoryClient;
    // OrderService HAS / USES an InventoryClient
    private final OrderRepository orderRepository;

    public OrderService(InventoryClient inventoryClient, OrderRepository orderRepository) {
        this.inventoryClient = inventoryClient;
        this.orderRepository = orderRepository;
    }

    public Order process(Order order) {
        boolean allAvailable = true;

        for (var item: order.getItems()) {
            boolean available = inventoryClient.isAvailable(item.getProductId(),item.getQuantity());
            if (!available) {
                allAvailable = false;
                break;
            }
        }
        if (allAvailable) {
            order.confirm();
        }else {
            order.reject();
        }
return orderRepository.save(order);
    }

}
