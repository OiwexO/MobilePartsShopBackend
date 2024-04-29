package com.nulp.mobilepartsshop.api.v1.order.dto.response;

import com.nulp.mobilepartsshop.api.v1.user.dto.response.AddressResponse;
import com.nulp.mobilepartsshop.core.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private List<OrderItemResponse> orderItems;

    private Double price;

    private OrderStatus status;

    private Date date;

    private Long customerId;

    private Long staffId;

    private AddressResponse shippingAddress;
}
