package com.nulp.mobilepartsshop.core.repository.order;

import com.nulp.mobilepartsshop.core.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
