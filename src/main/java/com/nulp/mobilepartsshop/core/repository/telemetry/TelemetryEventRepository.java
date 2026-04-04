package com.nulp.mobilepartsshop.core.repository.telemetry;

import com.nulp.mobilepartsshop.core.entity.telemetry.TelemetryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetryEventRepository extends JpaRepository<TelemetryEvent, Long> {
    // Methods for future business analytics (e.g., count events by type and date) can be added here
}
