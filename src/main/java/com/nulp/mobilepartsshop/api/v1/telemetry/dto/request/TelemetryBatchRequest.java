package com.nulp.mobilepartsshop.api.v1.telemetry.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TelemetryBatchRequest {

    private List<TelemetryEventRequest> events;
}
