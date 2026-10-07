package org.orderflow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.orderflow.domain.OrderItem;

public class OrderItemTest {

    @Test
    void constructor_shouldCreateValidOrderItem() {

        OrderItem item =
                new OrderItem("P100", 1);

        assertEquals("P100", item.getProductId());
        assertEquals(1, item.getQuantity());
    }

    @Test
    void constructor_shouldRejectNullProductId() {
        assertThrows(IllegalArgumentException.class,
                () -> new OrderItem(null,2));
    }

    @Test
    void constructor_shouldRejectBlankProductId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new OrderItem("   ", 2)
        );
    }

    @Test
    void constructor_shouldRejectZeroQuantity() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new OrderItem("P100", 0)
        );
    }

    @Test
    void constructor_shouldRejectNegativeQuantity() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new OrderItem("P100", -1)
        );
    }
}
