package com.nulp.mobilepartsshop.core.entity.part.manufacturer;

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
@Table(name = "manufacturers", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Manufacturer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    private ManufacturerLogo logo;
}
