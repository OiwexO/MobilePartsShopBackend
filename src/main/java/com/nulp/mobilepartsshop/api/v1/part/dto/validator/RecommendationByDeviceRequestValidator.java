package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.RecommendationByDeviceRequest;

public class RecommendationByDeviceRequestValidator extends RequestValidator<RecommendationByDeviceRequest> {

    @Override
    public boolean isValidRequest(RecommendationByDeviceRequest request) {
        final Long deviceId = request.getDeviceId();
        return isValidId(deviceId);
    }
}
