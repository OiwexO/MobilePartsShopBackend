package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.utils.MultipartFileUtils;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.ManufacturerRequest;
import org.springframework.web.multipart.MultipartFile;

public class ManufacturerRequestValidator extends RequestValidator {

    public static boolean isValidDto(ManufacturerRequest manufacturerRequest) {
        String name = manufacturerRequest.getName();
        MultipartFile logo = manufacturerRequest.getLogo();
        return isValidName(name) && MultipartFileUtils.isValidFileForManufacturerLogo(logo);
    }

    public static boolean isValidName(String name) {
        return isValidString(name);
    }
}
