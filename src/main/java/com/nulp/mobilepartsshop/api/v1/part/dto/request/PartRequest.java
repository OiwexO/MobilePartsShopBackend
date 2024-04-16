package com.nulp.mobilepartsshop.api.v1.part.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartRequest {

    private Double price;

    private Integer quantity;

    private String model;

    private String specifications;

    private Long manufacturerId;

    private Long deviceTypeId;

    private Long partTypeId;

    private List<MultipartFile> partImages;
}
