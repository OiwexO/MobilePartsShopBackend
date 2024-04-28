package com.nulp.mobilepartsshop.api.v1.order.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.order.dto.response.OrderItemResponse;
import com.nulp.mobilepartsshop.api.v1.order.dto.response.OrderResponse;
import com.nulp.mobilepartsshop.api.v1.user.dto.mapper.AddressMapper;
import com.nulp.mobilepartsshop.api.v1.user.dto.response.AddressResponse;
import com.nulp.mobilepartsshop.core.entity.order.Order;

import java.util.List;

public class OrderMapper extends Mapper<Order, OrderResponse> {

    private final OrderItemMapper orderItemMapper = new OrderItemMapper();

    private final AddressMapper addressMapper = new AddressMapper();

    public OrderMapper() {}

    @Override
    public OrderResponse toResponse(Order entity) {
        final List<OrderItemResponse> orderItems = orderItemMapper.toResponseList(entity.getOrderItems());
        final AddressResponse shippingAddress = addressMapper.toResponse(entity.getShippingAddress());
        return OrderResponse.builder()
                .id(entity.getId())
                .orderItems(orderItems)
                .price(entity.getPrice())
                .status(entity.getStatus())
                .date(entity.getDate())
                .customerId(entity.getCustomer().getId())
                .staffId(entity.getStaff().getId())
                .shippingAddress(shippingAddress)
                .build();
    }
}
