package by.sapra.orderHub.orders.interfaces.web.v1.controllers;

import by.sapra.orderHub.orders.domain.exception.OrderNotPresentException;
import by.sapra.orderHub.orders.interfaces.web.v1.model.ErrorResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(OrderNotPresentException.class)
    public ResponseEntity<ErrorResource> handleOrderNotPresentException(OrderNotPresentException ex) {

        return ResponseEntity.badRequest()
                .body(new ErrorResource("404", "Заказ не найден", ex.data()));
    }
}
