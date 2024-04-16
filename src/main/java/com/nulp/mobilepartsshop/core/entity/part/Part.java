package com.nulp.mobilepartsshop.core.entity.part;

import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
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
@Table(name = "parts")
public class Part {

    @Id
    @GeneratedValue
    private Long id;

    private Double price;

    private Integer quantity;

    private String model;

    private String specifications;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "device_type_id")
    private DeviceType deviceType;

    @ManyToOne
    @JoinColumn(name = "part_type_id")
    private PartType partType;

    @OneToOne(mappedBy = "part")
    @PrimaryKeyJoinColumn
    private PartImage partImage;
}
