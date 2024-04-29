package com.nulp.mobilepartsshop.api.v1.user.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.user.dto.request.DeviceRequest;

public class DeviceRequestValidator extends RequestValidator<DeviceRequest> {

    public DeviceRequestValidator() {}

    @Override
    public boolean isValidRequest(DeviceRequest request) {
        final String model = request.getModel();
        final String specifications = request.getSpecifications();
        final Long manufacturerId = request.getManufacturerId();
        final Long deviceTypeId = request.getDeviceTypeId();
        return isValidString(model) && isValidString(specifications) && isValidId(manufacturerId) && isValidId(deviceTypeId);
    }
}
