package by.sapra.orderHub.orders.interfaces.web.v1.model;

import by.sapra.orderHub.orders.domain.command.CreateOrderCommand;
import by.sapra.orderHub.orders.domain.entityObjects.OrderItem;
import by.sapra.orderHub.orders.domain.entityObjects.OrderItems;
import by.sapra.orderHub.orders.domain.valueObjects.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record CreateOrderPayload(
        @Valid
        @NotNull
        List<ProductResource> products
) {
    public CreateOrderCommand toCommand() {
        var productMap = products.stream()
                .collect(Collectors.toMap(Function.identity(), e -> 1, Integer::sum));

        OrderItems items = castToItems(productMap);

        return new CreateOrderCommand(items);
    }

    private OrderItems castToItems(Map<ProductResource, Integer> productMap) {
        OrderItems orderItems = new OrderItems();

        List<OrderItem> items = new ArrayList<>();

        for (var product : productMap.keySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(productMap.get(product));
            orderItem.setProduct(new Product(
                    product.productId(), product.productName(), product.price()
            ));
            items.add(orderItem);
        }
        orderItems.setItemList(items);
        return orderItems;
    }
}
