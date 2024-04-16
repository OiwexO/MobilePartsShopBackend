package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.MultipartFileUtils;
import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartRequest;
import org.springframework.web.multipart.MultipartFile;

public class PartRequestValidator extends RequestValidator<PartRequest> {

    @Override
    public boolean isValidRequest(PartRequest request) {
        final Double price = request.getPrice();
        if (price == null || price <= 0) {
            return false;
        }
        final Integer quantity = request.getQuantity();
        if (quantity == null || quantity <= 0) {
            return false;
        }
        final String model = request.getModel();
        final String specifications = request.getSpecifications();
        if (!isValidString(model) || !isValidString(specifications)) {
            return false;
        }
        final Long manufacturerId = request.getManufacturerId();
        final Long deviceTypeId = request.getDeviceTypeId();
        final Long partTypeId = request.getPartTypeId();
        if (!isValidId(manufacturerId) || !isValidId(deviceTypeId) || !isValidId(partTypeId)) {
            return false;
        }
        final MultipartFile image = request.getPartImage();
        if (image == null) {
            return false;
        }
        if (!image.isEmpty()) {
            return MultipartFileUtils.isValidFileForPartImage(image);
        }
        return true;
    }
}
