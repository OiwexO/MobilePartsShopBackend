package com.nulp.mobilepartsshop.core.entity.part;

import com.nulp.mobilepartsshop.core.enums.ImageType;
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

    @Enumerated(EnumType.STRING)
    private ImageType imageType;
}