package com.nulp.mobilepartsshop.api.v1.part.service;

import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.enums.SortingMode;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;

import java.util.List;

public interface PartRecommendationService {

    List<Part> findByManufacturerIdAndDeviceTypeIdAndPartTypeIdOrderByPrice(
            Long manufacturerId,
            Long deviceTypeId,
            Long partTypeId,
            SortingMode sortingMode
    );

    List<Part> findPartsForDeviceOrderByPrice(
            Long deviceId,
            SortingMode sortingMode
    ) throws EntityNotFoundException;
}
