package com.nulp.mobilepartsshop.core.entity.order;

import com.nulp.mobilepartsshop.core.entity.user.Address;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private Double price;

    private OrderStatus status;

    private Date date;

    @ManyToOne
    private User customer;

    @ManyToOne
    private User staff;

    @ManyToOne
    private Address shippingAddress;
}