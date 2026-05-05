package by.sapra.orderHub.orders.domain.valueObjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long productId;
    private String productName;
    private BigDecimal price;
}
