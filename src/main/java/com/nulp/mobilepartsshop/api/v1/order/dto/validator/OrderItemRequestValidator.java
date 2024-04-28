package com.nulp.mobilepartsshop.api.v1.order.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.order.dto.request.OrderItemRequest;

public class OrderItemRequestValidator extends RequestValidator<OrderItemRequest> {

    public OrderItemRequestValidator() {}

    @Override
    public boolean isValidRequest(OrderItemRequest request) {
        final Long partId = request.getPartId();
        final Integer quantity = request.getQuantity();
        return isValidId(partId) && quantity != null && quantity > 0;
    }
}
