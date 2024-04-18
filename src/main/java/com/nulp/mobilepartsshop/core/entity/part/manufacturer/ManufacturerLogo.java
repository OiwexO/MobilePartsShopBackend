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
@Table(name = "manufacturer_logos")
public class ManufacturerLogo {

    @Id
    private Long id;

    private String filepath;

    @OneToOne
    @MapsId
    private Manufacturer manufacturer;
}
