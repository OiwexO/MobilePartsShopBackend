package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.DtoValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.DeviceTypeRequest;

public class DeviceTypeDtoValidator extends DtoValidator {

    public static boolean isValidDto(DeviceTypeRequest deviceTypeRequest) {
        final String nameEn = deviceTypeRequest.getNameEn();
        final String nameUk = deviceTypeRequest.getNameUk();
        return nameEn != null && !nameEn.isBlank() && nameUk != null && !nameUk.isBlank();
    }

}
