package by.sapra.orderHub.orders.application.command;

import by.sapra.orderHub.orders.domain.aggregate.Order;
import by.sapra.orderHub.orders.domain.command.CreateOrderCommand;
import by.sapra.orderHub.orders.infrastructure.data.jpa.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final OrderRepository repository;

    public Order createOrder(CreateOrderCommand command) {
        return repository.save(new Order(command));
    }
}
