package com.nulp.mobilepartsshop.core.entity.part;

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
@Table(name = "part_images")
public class PartImage {

    @Id
    private Long id;

    private String filepath;

    @OneToOne
    @MapsId
    private Part part;
}