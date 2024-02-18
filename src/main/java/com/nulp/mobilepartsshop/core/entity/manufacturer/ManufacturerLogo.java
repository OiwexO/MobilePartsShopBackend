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

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @OneToOne
    @MapsId
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
}
