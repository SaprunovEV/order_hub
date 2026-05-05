package by.sapra.orderHub.orders.infrastructure.data.jpa;

import by.sapra.orderHub.orders.domain.aggregate.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
