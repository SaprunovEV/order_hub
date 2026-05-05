package by.sapra.orderHub.orders.interfaces.web.v1.model;

import by.sapra.orderHub.orders.domain.aggregate.Order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResource(
        String id,
        List<OrderItemResource> items,
        BigDecimal total
) {
    public static OrderResource from(Order order) {
        return new OrderResource(
                order.getOrderNumber().getBusinessId(),
                order.getItems().getItemList().stream().map(OrderItemResource::from).toList(),
                order.totalPrice()
        );
    }
}
