package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.DtoValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartTypeRequest;

public class PartTypeDtoValidator extends DtoValidator {

    public static boolean isValidDto(PartTypeRequest partTypeRequest) {
        final String nameEn = partTypeRequest.getNameEn();
        final String nameUk = partTypeRequest.getNameUk();
        return nameEn != null && !nameEn.isBlank() && nameUk != null && !nameUk.isBlank();
    }

}
