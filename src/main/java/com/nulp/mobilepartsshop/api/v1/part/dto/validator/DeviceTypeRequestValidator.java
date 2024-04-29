package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.DeviceTypeRequest;

public class DeviceTypeRequestValidator extends RequestValidator<DeviceTypeRequest> {

    public DeviceTypeRequestValidator() {}

    @Override
    public boolean isValidRequest(DeviceTypeRequest request) {
        final String nameEn = request.getNameEn();
        final String nameUk = request.getNameUk();
        return isValidString(nameEn) && isValidString(nameUk);
    }

}
