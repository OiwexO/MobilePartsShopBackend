package com.nulp.mobilepartsshop.core.entity.order;

import com.nulp.mobilepartsshop.core.entity.part.Part;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    private int quantity;
}

