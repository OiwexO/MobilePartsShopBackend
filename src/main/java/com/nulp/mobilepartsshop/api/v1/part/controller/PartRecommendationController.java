package com.nulp.mobilepartsshop.api.v1.part.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.part.dto.mapper.PartMapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.RecommendationByCriteriaRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.RecommendationByDeviceRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.PartResponse;
import com.nulp.mobilepartsshop.api.v1.part.dto.validator.RecommendationByCriteriaRequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.validator.RecommendationByDeviceRequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.service.PartRecommendationService;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.enums.SortingMode;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PartRecommendationController.MAPPING)
@RequiredArgsConstructor
public class PartRecommendationController {

    public static final String MAPPING = ApiConstants.PART_MAPPING_V1 + "/recommendations";

    private final PartRecommendationService recommendationService;

    private final RecommendationByCriteriaRequestValidator criteriaRequestValidator = new RecommendationByCriteriaRequestValidator();

    private final RecommendationByDeviceRequestValidator deviceRequestValidator = new RecommendationByDeviceRequestValidator();

    private final PartMapper mapper = new PartMapper();

    @PostMapping("/criteria")
    public ResponseEntity<List<PartResponse>> getPartsByCriteria(@RequestBody RecommendationByCriteriaRequest request) {
        if (!criteriaRequestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        SortingMode sortingMode = request.getSortAscending() ? SortingMode.ASCENDING : SortingMode.DESCENDING;
        List<Part> parts = recommendationService.findByManufacturerIdAndDeviceTypeIdAndPartTypeIdOrderByPrice(
                    request.getManufacturerId(),
                    request.getDeviceTypeId(),
                    request.getPartTypeId(),
                    sortingMode
            );
        List<PartResponse> responseList = mapper.toResponseList(parts);
        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/device")
    public ResponseEntity<List<PartResponse>> getPartsForDevice(@RequestBody RecommendationByDeviceRequest request) {
        if (!deviceRequestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        SortingMode sortingMode = request.getSortAscending() ? SortingMode.ASCENDING : SortingMode.DESCENDING;
        List<Part> parts;
        try {
            parts = recommendationService.findPartsForDeviceOrderByPrice(request.getDeviceId(), sortingMode);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        List<PartResponse> responseList = mapper.toResponseList(parts);
        return ResponseEntity.ok(responseList);
    }
}
