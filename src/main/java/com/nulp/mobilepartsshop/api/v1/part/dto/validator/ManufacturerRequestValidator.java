package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.utils.MultipartFileUtils;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.ManufacturerRequest;
import org.springframework.web.multipart.MultipartFile;

public class ManufacturerRequestValidator extends RequestValidator<ManufacturerRequest> {

    public ManufacturerRequestValidator() {}

    @Override
    public boolean isValidRequest(ManufacturerRequest request) {
        final String name = request.getName();
        final MultipartFile logo = request.getLogo();
        return isValidName(name) && MultipartFileUtils.isValidFileForManufacturerLogo(logo);
    }

    public boolean isValidName(String name) {
        return isValidString(name);
    }
}
