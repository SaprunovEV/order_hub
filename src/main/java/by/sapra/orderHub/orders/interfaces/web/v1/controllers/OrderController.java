package by.sapra.orderHub.orders.interfaces.web.v1.controllers;

import by.sapra.orderHub.orders.application.command.OrderCommandService;
import by.sapra.orderHub.orders.application.queries.OrderQueryService;
import by.sapra.orderHub.orders.domain.query.OrderByNumberQuery;
import by.sapra.orderHub.orders.interfaces.web.v1.model.CreateOrderPayload;
import by.sapra.orderHub.orders.interfaces.web.v1.model.OrderResource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderCommandService commandService;
    private final OrderQueryService queryService;

    @PostMapping
    public ResponseEntity<OrderResource> createOrder(@RequestBody @Valid CreateOrderPayload payload) {
        OrderResource resource = OrderResource.from(commandService.createOrder(payload.toCommand()));
        return ResponseEntity.created(URI.create("/api/v1/orders/" + resource.id()))
                .body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResource> findOrder(@PathVariable String id) {
        return ResponseEntity.ok()
                .body(OrderResource.from(queryService.findOrder(new OrderByNumberQuery(id))));
    }
}
