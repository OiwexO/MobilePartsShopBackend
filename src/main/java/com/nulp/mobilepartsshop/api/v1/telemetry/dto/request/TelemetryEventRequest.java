package com.nulp.mobilepartsshop.api.v1.telemetry.dto.request;

import com.nulp.mobilepartsshop.core.entity.telemetry.EventType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TelemetryEventRequest {

    private EventType eventType;

    private Long userId;

    private String payload;

    private LocalDateTime clientTimestamp;

    private String sourceApp;
}
