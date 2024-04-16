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
    @GeneratedValue
    private Long id;

    private String filepath;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "part_id")
    private Part part;
}