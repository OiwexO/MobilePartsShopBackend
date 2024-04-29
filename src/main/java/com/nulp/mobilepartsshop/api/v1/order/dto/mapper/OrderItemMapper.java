package com.nulp.mobilepartsshop.api.v1.order.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.order.dto.response.OrderItemResponse;
import com.nulp.mobilepartsshop.api.v1.part.dto.mapper.PartMapper;
import com.nulp.mobilepartsshop.core.entity.order.OrderItem;

public class OrderItemMapper extends Mapper<OrderItem, OrderItemResponse> {

    private final PartMapper partMapper = new PartMapper();

    public OrderItemMapper() {}

    @Override
    public OrderItemResponse toResponse(OrderItem entity) {
        return OrderItemResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .part(partMapper.toResponse(entity.getPart()))
                .quantity(entity.getQuantity())
                .build();
    }
}
