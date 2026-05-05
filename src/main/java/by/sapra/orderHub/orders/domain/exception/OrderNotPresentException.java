package by.sapra.orderHub.orders.domain.exception;

import by.sapra.orderHub.orders.domain.query.OrderByNumberQuery;

public class OrderNotPresentException extends RuntimeException {
    private final OrderByNumberQuery data;

    public OrderNotPresentException(OrderByNumberQuery query) {
        super("Не найден заказ под номером: " + query.orderNumber());
        this.data = query;
    }

    public Object data() {
        return data;
    }
}
