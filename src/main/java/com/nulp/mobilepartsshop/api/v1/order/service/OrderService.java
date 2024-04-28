package com.nulp.mobilepartsshop.api.v1.order.service;

import com.nulp.mobilepartsshop.api.v1.order.dto.request.OrderRequest;
import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAllOrders();

    List<Order> getOrdersByCustomerId(Long customerId) throws EntityNotFoundException;

    List<Order> getOrdersByStaffId(Long staffId) throws EntityNotFoundException;

    Optional<Order> getOrderById(Long id);

    Order createOrder(Long customerId, OrderRequest request);

    Optional<Order> updateOrder(Long id, OrderRequest request);

    boolean deleteOrder(Long id);
}
