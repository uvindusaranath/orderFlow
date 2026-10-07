package org.orderflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.orderflow.domain.InvalidOrderStateException;
import org.orderflow.domain.Order;
import org.orderflow.domain.OrderItem;
import org.orderflow.domain.OrderStatus;

import java.util.List;

class OrderTest {
Order order;
    @BeforeEach
    void setup() {
        order = new Order("c001",List.of(new OrderItem("100",20)));
    }

    private Order createOrder() {
        return new Order("c001", List.of(new OrderItem("p01",50)));
    }


    @Test
    void newOrder_shouldStartInCreatedState(){
        OrderItem item = new OrderItem("001",20);
        Order order = new Order("c101", List.of(item));
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    @Test
    void constructor_shouldCreateOrderInCreatedState() {
        Order order = createOrder();
        assertEquals(OrderStatus.CREATED,order.getStatus());
    }

    @Test
    void confirm_shouldMoveCreatedOrderToConfirmed() {
        order.confirm();
        assertEquals(
                OrderStatus.CONFIRMED,
                order.getStatus()
        );

    }

    @Test
    void reject_shouldMoveCreatedOrderToRejected() {

        Order order = createOrder();

        order.reject();

        assertEquals(
                OrderStatus.REJECTED,
                order.getStatus()
        );
    }

    @Test
    void cancel_shouldMoveConfirmedOrderToCancelled() {

        Order order = createOrder();

        order.confirm();
        order.cancel();

        assertEquals(
                OrderStatus.CANCELLED,
                order.getStatus()
        );
    }

    @Test
    void cancel_shouldRejectCreatedOrder() {
        Order order = createOrder();
        assertThrows(InvalidOrderStateException.class,
                order::cancel);

    }

    @Test
    void cancel_shouldRejectCreatedOrderAndKeepStateUnchanged() {

        Order order = createOrder();

        assertThrows(
                InvalidOrderStateException.class,
                order::cancel
        );

        assertEquals(
                OrderStatus.CREATED,
                order.getStatus()
        );
    }

    @Test
    void confirm_shouldRejectAlreadyConfirmedOrder() {

        Order order = createOrder();
        order.confirm();

        assertThrows(
                InvalidOrderStateException.class,
                order::confirm
        );

        assertEquals(
                OrderStatus.CONFIRMED,
                order.getStatus()
        );
    }

    @Test
    void reject_shouldRejectConfirmedOrder() {

        Order order = createOrder();
        order.confirm();

        assertThrows(
                InvalidOrderStateException.class,
                order::reject
        );

        assertEquals(
                OrderStatus.CONFIRMED,
                order.getStatus()
        );
    }

    @Test
    void confirm_shouldRejectRejectedOrder() {

        Order order = createOrder();
        order.reject();

        assertThrows(
                InvalidOrderStateException.class,
                order::confirm
        );

        assertEquals(
                OrderStatus.REJECTED,
                order.getStatus()
        );
    }

    @Test
    void cancel_shouldRejectRejectedOrder() {

        Order order = createOrder();
        order.reject();

        assertThrows(
                InvalidOrderStateException.class,
                order::cancel
        );

        assertEquals(
                OrderStatus.REJECTED,
                order.getStatus()
        );
    }

    @Test
    void confirm_shouldRejectCancelledOrder() {

        Order order = createOrder();
        order.confirm();
        order.cancel();

        assertThrows(
                InvalidOrderStateException.class,
                order::confirm
        );

        assertEquals(
                OrderStatus.CANCELLED,
                order.getStatus()
        );
    }

    @Test
    void constructor_shouldRejectNullItems() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Order("C100", null)
        );
    }
    @Test
    void constructor_shouldRejectEmptyItems() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Order(
                        "C100",
                        List.of()
                )
        );
    }
}
