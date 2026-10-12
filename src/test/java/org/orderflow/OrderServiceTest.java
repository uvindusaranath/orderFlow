package org.orderflow;

import org.junit.jupiter.api.Test;
import org.orderflow.application.OrderService;
import org.orderflow.application.port.InventoryClient;
import org.orderflow.application.port.OrderRepository;
import org.orderflow.domain.Order;
import org.orderflow.domain.OrderItem;
import org.orderflow.domain.OrderStatus;
import org.orderflow.infrastructure.inventory.FakeInventoryClient;
import org.orderflow.infrastructure.persistence.InMemoryOrderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderServiceTest {


    @Test
    void shouldConfirmAndSaveOrderWhenAllItemsAreAvailable() {

        InventoryClient inventoryClient =
                new FakeInventoryClient(true);

        OrderRepository repository =
                new InMemoryOrderRepository();

        OrderService service =
                new OrderService(
                        inventoryClient,
                        repository
                );

        // Arrange
        Order order =
                new Order(
                        "customer-1",
                        List.of(
                                new OrderItem("product-1", 2)
                        )
                );

        // Act
        Order result =
                service.createOrder(order);

        // Assert
        assertEquals(
                OrderStatus.CONFIRMED,
                result.getStatus()
        );

        assertEquals(
                1,
                repository.findAll().size()
        );

        assertEquals(
                OrderStatus.CONFIRMED,
                repository.findAll().get(0).getStatus()
        );
    }

    @Test
    void shouldRejectAndSaveOrderWhenInventoryIsUnavailable() {

        InventoryClient inventoryClient =
                new FakeInventoryClient(false);

        OrderRepository repository =
                new InMemoryOrderRepository();

        OrderService service =
                new OrderService(
                        inventoryClient,
                        repository
                );

        // Arrange
        Order order =
                new Order(
                        "customer-2",
                        List.of(
                                new OrderItem("product-10", 2)
                        )
                );

        // Act
        Order result =
                service.createOrder(order);


        // Assert
        // Assert
        assertEquals(
                OrderStatus.REJECTED,
                result.getStatus()
        );

        assertEquals(
                1,
                repository.findAll().size()
        );

        assertEquals(
                OrderStatus.REJECTED,
                repository.findAll().get(0).getStatus()
        );
    }

    @Test
    void shouldNotRejectOrderWhenInventoryCheckFailsTechnically() {

        InventoryClient inventoryClient =
                (productId, quantity) -> {
                    throw new RuntimeException("Inventory timeout");
                };

//        InventoryClient has one abstract method
//        ↓
//        lambda supplies that method body
//        ↓
//        Java creates an InventoryClient implementation


        OrderRepository repository =
                new InMemoryOrderRepository();

        OrderService service =
                new OrderService(
                        inventoryClient,
                        repository
                );

        Order order =
                new Order(
                        "customer-2",
                        List.of(
                                new OrderItem("product-10", 2)
                        )
                );


        assertThrows(
                RuntimeException.class,
                () -> service.createOrder(order)
        );
        assertEquals(
                OrderStatus.CREATED,
                order.getStatus()
        );
        assertEquals(
                0,
                repository.findAll().size()
        );

    }
}
