package com.nulp.mobilepartsshop.core.service.order;

import com.nulp.mobilepartsshop.api.v1.order.dto.request.OrderRequest;
import com.nulp.mobilepartsshop.api.v1.order.service.OrderService;
import com.nulp.mobilepartsshop.api.v1.part.service.PartService;
import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.repository.order.OrderRepository;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final PartService partService;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) throws EntityNotFoundException {
        User user = userRepository.findById(customerId).orElseThrow(EntityNotFoundException::new);
        return orderRepository.findAllByCustomerId(user.getId());
    }

    @Override
    public List<Order> getOrdersByStaffId(Long staffId) throws EntityNotFoundException {
        User user = userRepository.findById(staffId).orElseThrow(EntityNotFoundException::new);
        return orderRepository.findAllByStaffId(user.getId());
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order createOrder(Long customerId, OrderRequest request) {

        return null;
    }

    @Override
    public Optional<Order> updateOrder(Long id, OrderRequest request) {
        return Optional.empty();
    }

    @Override
    public boolean deleteOrder(Long id) {
        return false;
    }
}
