package by.sapra.orderHub.orders.domain.aggregate;

import by.sapra.orderHub.orders.domain.command.CreateOrderCommand;
import by.sapra.orderHub.orders.domain.entityObjects.OrderItem;
import by.sapra.orderHub.orders.domain.entityObjects.OrderItems;
import by.sapra.orderHub.orders.domain.valueObjects.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "businessId", column = @Column(name = "order_number", nullable = false, unique = true))
    private BusinessesId orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private OrderItems items;

    private Instant createAt;

    public Order(CreateOrderCommand command) {
        this.status = OrderStatus.CREATED;
        setItems(command.items());
        setBusinessesId();
    }

    public BigDecimal totalPrice() {
        if (items == null || items.getItemList() == null || items.getItemList().isEmpty())
            return BigDecimal.ZERO;

        List<OrderItem> itemList = items.getItemList();


        BigDecimal total = BigDecimal.ZERO;

        for (var item : itemList) {
            total = total.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        return total;
    }

    @PrePersist
    void prePersist() {
        createAt = Instant.now();
    }

    private void setBusinessesId() {
        this.orderNumber = new BusinessesId();
    }

    private void setItems(OrderItems items) {
        this.items = items;

        if (items != null && items.getItemList() != null) {
            for (OrderItem orderItem : items.getItemList()) {
                orderItem.setOrder(this);
            }
        }
    }
}
