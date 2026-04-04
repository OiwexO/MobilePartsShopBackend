package com.nulp.mobilepartsshop.api.v1.telemetry.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.telemetry.dto.request.TelemetryBatchRequest;
import com.nulp.mobilepartsshop.api.v1.telemetry.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TelemetryController.MAPPING)
@RequiredArgsConstructor
public class TelemetryController {

    public static final String MAPPING = ApiConstants.TELEMETRY_MAPPING_V1 + "/telemetry";

    private final TelemetryService telemetryService;

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> receiveTelemetryBatch(@RequestBody TelemetryBatchRequest batchRequest) {
        try {
            telemetryService.processBatch(batchRequest);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
