package org.orderflow.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
 private String customerId;
 private final List<OrderItem> items;
 //final here means the items field reference cannot later be reassigned to another List object.
    private OrderStatus status;
 public Order(String customerId, List<OrderItem> items) {
     this.customerId = customerId;
     this.items = new ArrayList<>(items);
     this.status = OrderStatus.CREATED;
 }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
     return List.copyOf(items);
     // This returns an unmodifiable copy of the current elements.
    }

    public void addItem(OrderItem item) {
     items.add(item);
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerId='" + customerId + '\'' +
                ", items=" + items + + '\'' +
                ", status=" + status +
                '}';
    }
}
