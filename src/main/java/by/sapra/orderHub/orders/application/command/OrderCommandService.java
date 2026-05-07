package by.sapra.orderHub.orders.application.command;

import by.sapra.orderHub.orders.domain.aggregate.Order;
import by.sapra.orderHub.orders.domain.command.CreateOrderCommand;
import by.sapra.orderHub.orders.infrastructure.data.jpa.OrderRepository;
import by.sapra.orderHub.orders.infrastructure.data.metrics.annotation.BusinessMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final OrderRepository repository;

    @BusinessMetrics(
            name = "orders.created",
            tags = {"operation=post", "type=write"}
    )
    public Order createOrder(CreateOrderCommand command) {
        return repository.save(new Order(command));
    }
}
