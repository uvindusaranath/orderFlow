package org.orderflow.infrastructure.inventory;

import org.orderflow.application.port.InventoryClient;

public class FakeInventoryClient implements InventoryClient {
    private final boolean available;

    public FakeInventoryClient(boolean available) {
        this.available = available;
    }

    @Override
    public boolean isAvailable(String productId, int quantity) {
        return available;
    }
}
