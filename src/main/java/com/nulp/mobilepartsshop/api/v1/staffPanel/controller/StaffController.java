package com.nulp.mobilepartsshop.api.v1.staffPanel.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.order.dto.mapper.OrderMapper;
import com.nulp.mobilepartsshop.api.v1.order.dto.response.OrderResponse;
import com.nulp.mobilepartsshop.api.v1.order.dto.validator.OrderRequestValidator;
import com.nulp.mobilepartsshop.api.v1.staffPanel.service.StaffService;
import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(StaffController.MAPPING)
@RequiredArgsConstructor
public class StaffController {

    public static final String MAPPING = ApiConstants.STAFF_MAPPING_V1 + "/staffs";

    private final StaffService staffService;

    private final OrderRequestValidator requestValidator = new OrderRequestValidator();

    private final OrderMapper mapper = new OrderMapper();

    @GetMapping("/orders/{staffId}")
    public ResponseEntity<List<OrderResponse>> getAssignedOrders(@PathVariable Long staffId) {
        if (!requestValidator.isValidId(staffId)) {
            return ResponseEntity.badRequest().build();
        }
        List<Order> orders;
        try {
            orders = staffService.getAssignedOrders(staffId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        List<OrderResponse> responseList = mapper.toResponseList(orders);
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/order/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId) {
        if (!requestValidator.isValidId(orderId)) {
            return ResponseEntity.badRequest().build();
        }
        Order order;
        try {
            order = staffService.updateOrderStatus(orderId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toResponse(order));
    }
}
