package com.nulp.mobilepartsshop.core.service.part;

import com.nulp.mobilepartsshop.api.v1.part.service.PartRecommendationService;
import com.nulp.mobilepartsshop.api.v1.user.service.DeviceService;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.user.Device;
import com.nulp.mobilepartsshop.core.enums.SortingMode;
import com.nulp.mobilepartsshop.core.repository.part.PartRecommendationRepository;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartRecommendationServiceImpl implements PartRecommendationService {

    private final PartRecommendationRepository recommendationRepository;

    private final DeviceService deviceService;

    @Override
    public List<Part> findByManufacturerIdAndDeviceTypeIdAndPartTypeIdOrderByPrice(
            Long manufacturerId,
            Long deviceTypeId,
            Long partTypeId,
            SortingMode sortingMode
    ) {
        return switch (sortingMode) {
            case ASCENDING ->
                    recommendationRepository.findByManufacturerIdAndDeviceTypeIdAndPartTypeIdOrderByPriceAsc(
                            manufacturerId,
                            deviceTypeId,
                            partTypeId
                    );
            case DESCENDING ->
                    recommendationRepository.findByManufacturerIdAndDeviceTypeIdAndPartTypeIdOrderByPriceDesc(
                            manufacturerId,
                            deviceTypeId,
                            partTypeId
                    );
        };
    }

    @Override
    public List<Part> findPartsForDeviceOrderByPrice(Long deviceId, SortingMode sortingMode) throws EntityNotFoundException {
        final Device device = deviceService.getDeviceById(deviceId).orElseThrow(EntityNotFoundException::new);
        final Long manufacturerId = device.getManufacturer().getId();
        final Long deviceTypeId = device.getDeviceType().getId();
        final String model = device.getModel();
        return switch (sortingMode) {
            case ASCENDING ->
                    recommendationRepository.findByManufacturerIdAndDeviceTypeIdAndDeviceModelsContainingIgnoreCaseOrderByPriceAsc(
                            manufacturerId,
                            deviceTypeId,
                            model
                    );
            case DESCENDING ->
                    recommendationRepository.findByManufacturerIdAndDeviceTypeIdAndDeviceModelsContainingIgnoreCaseOrderByPriceDesc(
                            manufacturerId,
                            deviceTypeId,
                            model
                    );
        };

    }

}
