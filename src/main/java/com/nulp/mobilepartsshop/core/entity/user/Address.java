package com.nulp.mobilepartsshop.core.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private Integer postalCode;

    private String country;

    private String state;

    private String city;

    private String street;

    private String buildingNumber;

    @OneToOne
    private User user;
}
