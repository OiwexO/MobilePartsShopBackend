package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartTypeRequest;

public class PartTypeRequestValidator extends RequestValidator<PartTypeRequest> {

    public PartTypeRequestValidator() {}

    @Override
    public boolean isValidRequest(PartTypeRequest request) {
        final String nameEn = request.getNameEn();
        final String nameUk = request.getNameUk();
        return isValidString(nameEn) && isValidString(nameUk);
    }

}
