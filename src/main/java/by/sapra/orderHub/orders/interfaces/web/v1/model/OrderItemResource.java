package by.sapra.orderHub.orders.interfaces.web.v1.model;

import by.sapra.orderHub.orders.domain.entityObjects.OrderItem;
import by.sapra.orderHub.orders.domain.valueObjects.Product;

import java.math.BigDecimal;

public record OrderItemResource(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity
) {
    public static OrderItemResource from(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        return new OrderItemResource(
                product.getProductId(),
                product.getProductName(),
                product.getPrice(),
                orderItem.getQuantity()
        );
    }
}
