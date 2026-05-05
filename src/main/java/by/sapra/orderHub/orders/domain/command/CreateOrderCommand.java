package by.sapra.orderHub.orders.domain.command;

import by.sapra.orderHub.orders.domain.entityObjects.OrderItems;

public record CreateOrderCommand(OrderItems items) {}
