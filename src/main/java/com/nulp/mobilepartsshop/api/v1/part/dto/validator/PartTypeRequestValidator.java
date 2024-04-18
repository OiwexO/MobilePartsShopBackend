package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartTypeRequest;

public class PartTypeRequestValidator extends RequestValidator {

    public static boolean isValidDto(PartTypeRequest partTypeRequest) {
        final String nameEn = partTypeRequest.getNameEn();
        final String nameUk = partTypeRequest.getNameUk();
        return isValidString(nameEn) && isValidString(nameUk);
    }

}
