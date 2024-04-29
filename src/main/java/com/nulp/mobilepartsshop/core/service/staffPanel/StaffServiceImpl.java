package com.nulp.mobilepartsshop.core.service.staffPanel;

import com.nulp.mobilepartsshop.api.v1.staffPanel.service.StaffService;
import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.enums.order.OrderStatus;
import com.nulp.mobilepartsshop.core.repository.order.OrderRepository;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import com.nulp.mobilepartsshop.core.service.email.EmailService;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final EmailService emailService;

    @Override
    public List<User> getAllStaffs() {
        return List.of();
    }

    @Override
    public List<Order> getAssignedOrders(Long staffId) throws EntityNotFoundException {
        User staff = userRepository.findById(staffId).orElseThrow(EntityNotFoundException::new);
        return staff.getAssignedOrders();
    }

    @Override
    public Order setOrderStatusProcessing(Long orderId) throws EntityNotFoundException {
        return updateOrderStatus(orderId, OrderStatus.PROCESSING);
    }

    @Override
    public Order setOrderStatusShipping(Long orderId) throws EntityNotFoundException {
        return updateOrderStatus(orderId, OrderStatus.SHIPPING);
    }

    @Override
    public Order setOrderStatusDelivered(Long orderId) throws EntityNotFoundException {
        Order order = updateOrderStatus(orderId, OrderStatus.DELIVERED);
        User customer = order.getCustomer();
        emailService.sendOrderDeliveredCustomerEmail(customer.getUsername());
        return order;
    }

    private Order updateOrderStatus(Long orderId, OrderStatus status) throws EntityNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
