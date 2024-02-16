package com.nulp.mobilepartsshop.core.entity.manufacturer;

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
    @Column(name = "manufacturer_id")
    private Long id;

    private String filepath;

    @OneToOne
    @MapsId
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
}
