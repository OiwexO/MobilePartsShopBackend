package com.nulp.mobilepartsshop.api.v1.part.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerRequest {

    private String name;

    private MultipartFile logo;
}
