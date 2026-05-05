package by.sapra.orderHub.orders.interfaces.web.v1.model;

public record ErrorResource(
        String code,
        String message,
        Object requestData
) {}
