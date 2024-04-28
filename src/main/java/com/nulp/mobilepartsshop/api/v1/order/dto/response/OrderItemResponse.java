package com.nulp.mobilepartsshop.api.v1.order.dto.response;

import com.nulp.mobilepartsshop.api.v1.part.dto.response.PartResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private Long id;

    private Long orderId;

    private PartResponse part;

    private Integer quantity;
}
