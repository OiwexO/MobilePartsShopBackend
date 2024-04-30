package com.nulp.mobilepartsshop.api.v1.staffPanel.service;

import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;

import java.util.List;

public interface StaffService {

    List<User> getAllStaffs();

    List<Order> getAssignedOrders(Long staffId) throws EntityNotFoundException;

    Order updateOrderStatus(Long orderId) throws EntityNotFoundException;
}
