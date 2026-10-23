package org.orderflow.api.dto;

import org.orderflow.domain.OrderStatus;

public record OrderResponse(String customerId, OrderStatus status) {
}
