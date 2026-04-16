package com.nulp.mobilepartsshop.core.service.telemetry;

import com.nulp.mobilepartsshop.api.v1.telemetry.dto.request.TelemetryBatchRequest;
import com.nulp.mobilepartsshop.api.v1.telemetry.service.TelemetryService;
import com.nulp.mobilepartsshop.core.entity.telemetry.TelemetryEvent;
import com.nulp.mobilepartsshop.core.repository.telemetry.TelemetryEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TelemetryServiceImpl implements TelemetryService {

    private final TelemetryEventRepository telemetryRepository;

    @Override
    @Transactional
    public void processBatch(TelemetryBatchRequest batchRequest) {
        LocalDateTime now = LocalDateTime.now();

        List<TelemetryEvent> eventsToSave = batchRequest.getEvents().stream()
                .map(request -> TelemetryEvent.builder()
                        .eventType(request.getEventType())
                        .userId(request.getUserId())
                        .payload(request.getPayload())
                        .clientTimestamp(request.getClientTimestamp())
                        .receivedAt(now)
                        .sourceApp(request.getSourceApp())
                        .build())
                .collect(Collectors.toList());

        telemetryRepository.saveAll(eventsToSave);
    }
}