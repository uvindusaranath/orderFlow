package org.orderflow.api.dto;


import java.util.List;

public record CreateOrderRequest(String customerId, List<OrderItemRequest> items) {
}
