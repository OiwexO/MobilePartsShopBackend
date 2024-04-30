package com.nulp.mobilepartsshop.core.repository.order;

import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.core.enums.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerId(Long customerId);

    List<Order> findAllByStaffId(Long staffId);

    List<Order> findAllByCustomerIdAndStatus(Long customerId, OrderStatus status);
}
