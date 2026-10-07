package org.orderflow.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
 private String customerId;
 private final List<OrderItem> items;

    //final here means the items field reference cannot later be reassigned to another List object.
private OrderStatus status;
 public Order(String customerId, List<OrderItem> items) {
     if (items == null || items.isEmpty()) {
         throw  new IllegalArgumentException("Order must contain at least one item");
     }
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

    public void confirm() {
     if (status != OrderStatus.CREATED) {
         throw new InvalidOrderStateException(
                 "Only CREATED orders can be confirmed"
         );
     }
        this.status = OrderStatus.CONFIRMED;
    }

    public void reject() {
        if (status != OrderStatus.CREATED) {
            throw  new InvalidOrderStateException(
                    "Only CREATED orders can be confirmed"
            );
        }
        this.status = OrderStatus.REJECTED;
    }

    public void cancel() {
     if (status != OrderStatus.CONFIRMED) {
         throw  new InvalidOrderStateException(
                 "Only CONFIRMED orders can be cancelled"
         );
     }
        this.status = OrderStatus.CANCELLED;
    }

    public OrderStatus getStatus() {
        return status;
    }

}
