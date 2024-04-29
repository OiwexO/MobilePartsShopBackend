package com.nulp.mobilepartsshop.core.entity.user;

import com.nulp.mobilepartsshop.core.entity.part.DeviceType;
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
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue
    private Long id;

    private String model;

    private String specifications;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "device_type_id")
    private DeviceType deviceType;
}