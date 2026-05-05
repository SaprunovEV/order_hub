package by.sapra.orderHub.orders.infrastructure.data.jpa;

import by.sapra.orderHub.orders.domain.aggregate.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "items.itemList")
    Optional<Order> findByOrderNumberBusinessId(String number);
}
