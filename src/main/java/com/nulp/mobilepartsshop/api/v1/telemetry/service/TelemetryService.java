package com.nulp.mobilepartsshop.api.v1.telemetry.service;

import com.nulp.mobilepartsshop.api.v1.telemetry.dto.request.TelemetryBatchRequest;

public interface TelemetryService {
    /**
     * Processes a batch of telemetry events and saves them to the database.
     *
     * @param batchRequest the batch of events from the client
     */
    void processBatch(TelemetryBatchRequest batchRequest);
}
