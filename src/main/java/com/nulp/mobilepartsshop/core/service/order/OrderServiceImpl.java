package com.nulp.mobilepartsshop.core.service.order;

import com.nulp.mobilepartsshop.api.v1.order.dto.request.OrderItemRequest;
import com.nulp.mobilepartsshop.api.v1.order.dto.request.OrderRequest;
import com.nulp.mobilepartsshop.api.v1.order.service.OrderService;
import com.nulp.mobilepartsshop.api.v1.part.service.PartService;
import com.nulp.mobilepartsshop.api.v1.staffPanel.service.StaffAssigningService;
import com.nulp.mobilepartsshop.api.v1.user.service.AddressService;
import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.core.entity.order.OrderItem;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.user.Address;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.enums.OrderStatus;
import com.nulp.mobilepartsshop.core.repository.order.OrderItemRepository;
import com.nulp.mobilepartsshop.core.repository.order.OrderRepository;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import com.nulp.mobilepartsshop.exception.staffPanel.NoAvailableStaffException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final double REGULAR_CUSTOMER_ORDER_PRICE_MULTIPLIER = 0.9;

    private static final int REGULAR_CUSTOMER_DELIVERED_ORDERS_COUNT = 10;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final UserRepository userRepository;

    private final PartService partService;

    private final AddressService addressService;

    private final StaffAssigningService staffAssigningService;

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
    public Order createOrder(Long customerId, OrderRequest request) throws EntityNotFoundException {
        User customer = userRepository.findById(customerId).orElseThrow(EntityNotFoundException::new);
        Order order = new Order();
        Address shippingAddress = addressService.getAddressById(request.getShippingAddressId()).orElseThrow(EntityNotFoundException::new);
        Order savedOrder = orderRepository.save(order);
        formOrder(savedOrder, request, customer);
        savedOrder.setStatus(OrderStatus.PENDING);
        savedOrder.setDate(new Date());
        savedOrder.setCustomer(customer);
        try {
            staffAssigningService.assignFreeStaffToOrder(savedOrder);
        } catch (NoAvailableStaffException e) {
            //TODO cancel order
        }
        savedOrder.setShippingAddress(shippingAddress);
        return orderRepository.save(savedOrder);
    }

    @Override
    public Optional<Order> updateOrder(Long id, OrderRequest request) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return Optional.empty();
        }
        Order order = optionalOrder.get();
        orderItemRepository.deleteAll(order.getOrderItems());
        try {
            formOrder(order, request, order.getCustomer());
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
        Optional<Address> optionalShippingAddress = addressService.getAddressById(request.getShippingAddressId());
        if (optionalShippingAddress.isEmpty()) {
            return Optional.empty();
        }
        order.setShippingAddress(optionalShippingAddress.get());
        return Optional.of(orderRepository.save(order));
    }

    @Override
    public boolean deleteOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return false;
        }
        Order order = optionalOrder.get();
        orderItemRepository.deleteAll(order.getOrderItems());
        orderRepository.delete(order);
        return true;
    }

    private void formOrder(Order order, OrderRequest request, User customer) throws EntityNotFoundException {
        List<OrderItem> orderItems = new ArrayList<>();
        double price = 0;
        for (OrderItemRequest orderItemRequest : request.getOrderItems()) {
            Part part = partService.getPartById(orderItemRequest.getPartId()).orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .part(part)
                    .quantity(orderItemRequest.getQuantity())
                    .build();
            price += part.getPrice() * orderItemRequest.getQuantity();
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        order.setOrderItems(orderItems);
        if (isDiscountAvailableForCustomer(customer)) {
            price *= REGULAR_CUSTOMER_ORDER_PRICE_MULTIPLIER;
        }
        order.setPrice(price);
    }

    private boolean isDiscountAvailableForCustomer(User customer) {
        List<Order> deliveredOrders = orderRepository.findAllByCustomerIdAndStatus(customer.getId(), OrderStatus.DELIVERED);
        return deliveredOrders.size() >= REGULAR_CUSTOMER_DELIVERED_ORDERS_COUNT;
    }

}
