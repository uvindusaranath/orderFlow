package org.orderflow.api;

import org.orderflow.api.dto.CreateOrderRequest;
import org.orderflow.api.dto.OrderResponse;
import org.orderflow.application.OrderService;
import org.orderflow.domain.Order;
import org.orderflow.domain.OrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody CreateOrderRequest request
    ) {

        List<OrderItem> items =
                request.items()
                        .stream()
                        .map(item ->
                                new OrderItem(
                                        item.productId(),
                                        item.quantity()
                                )
                        )
                        .toList();

        Order order =
                new Order(
                        request.customerId(),
                        items
                );

        Order createdOrder =
                orderService.createOrder(order);

        OrderResponse response =
                new OrderResponse(
                        createdOrder.getCustomerId(),
                        createdOrder.getStatus()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}

