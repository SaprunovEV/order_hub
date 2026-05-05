package by.sapra.orderHub.orders.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Embeddable
@Data
public class BusinessesId {
    @Column(unique = true, nullable = false)
    private String businessId;

    public BusinessesId() {
        this.businessId = UUID.randomUUID().toString();
    }
}
