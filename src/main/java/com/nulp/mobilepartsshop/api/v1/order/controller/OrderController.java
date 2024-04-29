package com.nulp.mobilepartsshop.api.v1.order.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.order.dto.mapper.OrderMapper;
import com.nulp.mobilepartsshop.api.v1.order.dto.request.OrderRequest;
import com.nulp.mobilepartsshop.api.v1.order.dto.response.OrderResponse;
import com.nulp.mobilepartsshop.api.v1.order.dto.validator.OrderRequestValidator;
import com.nulp.mobilepartsshop.api.v1.order.service.OrderService;
import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(OrderController.MAPPING)
@RequiredArgsConstructor
public class OrderController {

    public static final String MAPPING = ApiConstants.ORDER_MAPPING_V1 + "/orders";

    private final OrderService orderService;

    private final OrderRequestValidator requestValidator = new OrderRequestValidator();

    private final OrderMapper mapper = new OrderMapper();

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> responseList = mapper.toResponseList(orders);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getAllOrdersForCustomer(@PathVariable Long customerId) {
        if (!requestValidator.isValidId(customerId)) {
            return ResponseEntity.badRequest().build();
        }
        List<Order> orders;
        try {
            orders = orderService.getOrdersByCustomerId(customerId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
        List<OrderResponse> responseList = mapper.toResponseList(orders);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        if (!requestValidator.isValidId(orderId)) {
            return ResponseEntity.badRequest().build();
        }
        return orderService.getOrderById(orderId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable Long customerId, @RequestBody OrderRequest request) {
        if (!requestValidator.isValidId(customerId) || !requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            final Order order = orderService.createOrder(customerId, request);
            final OrderResponse response = mapper.toResponse(order);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            // invalid id passed as one of the fields
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable Long orderId,
            @RequestBody OrderRequest request
    ) {
        if (!requestValidator.isValidId(orderId) || !requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        return orderService.updateOrder(orderId, request)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        if (!requestValidator.isValidId(orderId)) {
            return ResponseEntity.badRequest().build();
        }
        if (orderService.deleteOrder(orderId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
