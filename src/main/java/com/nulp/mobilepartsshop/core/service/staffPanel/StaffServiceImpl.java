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

import java.util.ArrayList;
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
        List<Order> assignedOrders = staff.getAssignedOrders();
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : assignedOrders) {
            if (order.getStatus() != OrderStatus.COMPLETED && order.getStatus() != OrderStatus.CANCELED) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    @Override
    public Order getOrderById(Long orderId) throws EntityNotFoundException {
        return orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Order updateOrderStatus(Long orderId) throws EntityNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        OrderStatus status = order.getStatus();
        switch (status) {
            case PENDING:
                order.setStatus(OrderStatus.PROCESSING);
                break;
            case PROCESSING:
                order.setStatus(OrderStatus.SHIPPING);
                break;
            case SHIPPING:
                order.setStatus(OrderStatus.DELIVERED);
                User customer = order.getCustomer();
                emailService.sendOrderDeliveredCustomerEmail(customer.getUsername(), customer.getFirstname(), orderId);
                break;
            case DELIVERED:
                order.setStatus(OrderStatus.COMPLETED);
                break;
            case COMPLETED, CANCELED:
                return order;
        }
        return orderRepository.save(order);
    }
}
