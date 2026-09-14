package org.orderflow.domain;

public class OrderItem {
    private String productId;
    private int quantity;

public OrderItem(String productId, int quantity) {

    if (productId == null | productId.isBlank()) {
        throw  new IllegalArgumentException(
                "Product ID is required"
        );
    }
    if (quantity <= 0) {
        throw new IllegalArgumentException(
                "Quantity must be greater than zero"
        ); // fail-fast behavior
    }
    this.productId = productId;
    this.quantity = quantity;
}

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}


