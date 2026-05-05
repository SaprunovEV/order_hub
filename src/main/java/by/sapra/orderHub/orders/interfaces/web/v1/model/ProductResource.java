package by.sapra.orderHub.orders.interfaces.web.v1.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductResource(
        @NotNull
        @Positive
        Long productId,
        @NotEmpty
        String productName,
        @NotNull
        @Positive
        BigDecimal price
) {
        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                ProductResource that = (ProductResource) o;
                return Objects.equals(productId, that.productId) && Objects.equals(price, that.price) && Objects.equals(productName, that.productName);
        }

        @Override
        public int hashCode() {
                return Objects.hash(productId);
        }
}
