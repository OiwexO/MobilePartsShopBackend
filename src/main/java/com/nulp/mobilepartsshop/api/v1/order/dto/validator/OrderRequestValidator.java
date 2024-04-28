package com.nulp.mobilepartsshop.api.v1.order.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.order.dto.request.OrderItemRequest;
import com.nulp.mobilepartsshop.api.v1.order.dto.request.OrderRequest;

import java.util.List;

public class OrderRequestValidator extends RequestValidator<OrderRequest> {

    private final OrderItemRequestValidator orderItemRequestValidator = new OrderItemRequestValidator();

    public OrderRequestValidator() {}

    @Override
    public boolean isValidRequest(OrderRequest request) {
        final List<OrderItemRequest> orderItems = request.getOrderItems();
        if (orderItems == null || orderItems.isEmpty()) {
            return false;
        }
        for (OrderItemRequest orderItem : orderItems) {
            if (!orderItemRequestValidator.isValidRequest(orderItem)) {
                return false;
            }
        }
        final Long shippingAddressId = request.getShippingAddressId();
        return isValidId(shippingAddressId);
    }
}
