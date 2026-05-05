package by.sapra.orderHub.orders.application.queries;

import by.sapra.orderHub.orders.domain.aggregate.Order;
import by.sapra.orderHub.orders.domain.exception.OrderNotPresentException;
import by.sapra.orderHub.orders.domain.query.OrderByNumberQuery;
import by.sapra.orderHub.orders.infrastructure.data.jpa.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
    private final OrderRepository repository;

    public Order findOrder(OrderByNumberQuery query) {
        return repository.findByOrderNumberBusinessId(query.orderNumber()).orElseThrow(() ->
            new OrderNotPresentException(query));
    }
}
