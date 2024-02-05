package com.nulp.mobilepartsshop.core.entity.manufacturer;

import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
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
@Table(name = "manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "manufacturer")
    private ManufacturerLogo logo;
}
