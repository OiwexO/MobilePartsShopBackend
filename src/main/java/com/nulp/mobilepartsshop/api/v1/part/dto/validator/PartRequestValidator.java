package com.nulp.mobilepartsshop.api.v1.part.dto.validator;

import com.nulp.mobilepartsshop.api.utils.MultipartFileUtils;
import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PartRequestValidator extends RequestValidator {

    public static boolean isValidDto(PartRequest partRequest) {
        final Double price = partRequest.getPrice();
        if (price == null || price <= 0) {
            return false;
        }
        final Integer quantity = partRequest.getQuantity();
        if (quantity == null || quantity <= 0) {
            return false;
        }
        final String model = partRequest.getModel();
        final String specifications = partRequest.getSpecifications();
        if (!isValidString(model) || !isValidString(specifications)) {
            return false;
        }
        final Long manufacturerId = partRequest.getManufacturerId();
        final Long deviceTypeId = partRequest.getDeviceTypeId();
        final Long partTypeId = partRequest.getPartTypeId();
        if (!isValidId(manufacturerId) || !isValidId(deviceTypeId) || !isValidId(partTypeId)) {
            return false;
        }
        final List<MultipartFile> images = partRequest.getPartImages();
        if (images == null) {
            return false;
        }
        for (MultipartFile image : images) {
            if (!MultipartFileUtils.isValidFileForPartImage(image)) {
                return false;
            }
        }
        return true;
    }
}
