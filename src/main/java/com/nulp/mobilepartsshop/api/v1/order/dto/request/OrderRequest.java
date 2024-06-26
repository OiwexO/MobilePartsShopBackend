package com.nulp.mobilepartsshop.api.v1.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private List<OrderItemRequest> orderItems;

    private Long shippingAddressId;
}
