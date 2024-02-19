package com.nulp.mobilepartsshop.api.v1.partType.dto;

import com.nulp.mobilepartsshop.api.utils.DtoValidator;

public class PartTypeDtoValidator extends DtoValidator {

    public static boolean isValidDto(PartTypeDto partTypeDto) {
        final String nameEn = partTypeDto.getNameEn();
        final String nameUk = partTypeDto.getNameUk();
        return nameEn != null && !nameEn.isBlank() && nameUk != null && !nameUk.isBlank();
    }
}
