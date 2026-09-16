package org.orderflow.application.port;

public interface InventoryClient {
    boolean isAvailable(String productId, int quantity);
}
