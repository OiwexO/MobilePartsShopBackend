package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.RecommendationByCriteriaRequest;

public class RecommendationByCriteriaRequestValidator extends RequestValidator<RecommendationByCriteriaRequest> {

    @Override
    public boolean isValidRequest(RecommendationByCriteriaRequest request) {
        final Long manufacturerId = request.getManufacturerId();
        final Long deviceTypeId = request.getDeviceTypeId();
        final Long partTypeId = request.getPartTypeId();
        return isValidCriterion(manufacturerId) && isValidCriterion(deviceTypeId) && isValidCriterion(partTypeId);
    }

    private boolean isValidCriterion(Long criterionId) {
        return criterionId != null && criterionId >= 0;
    }
}
