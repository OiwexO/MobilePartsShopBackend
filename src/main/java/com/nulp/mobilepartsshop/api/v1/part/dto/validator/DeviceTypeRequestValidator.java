package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.DeviceTypeRequest;

public class DeviceTypeRequestValidator extends RequestValidator {

    public static boolean isValidDto(DeviceTypeRequest deviceTypeRequest) {
        final String nameEn = deviceTypeRequest.getNameEn();
        final String nameUk = deviceTypeRequest.getNameUk();
        return isValidString(nameEn) && isValidString(nameUk);
    }

}
