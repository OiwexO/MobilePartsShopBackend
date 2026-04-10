package com.nulp.mobilepartsshop.core.repository.telemetry.projection;

public interface StaffEfficiencyProjection {
    Integer getTotalProcessedOrders();
    Double getAverageProcessingTimeSeconds();
}
