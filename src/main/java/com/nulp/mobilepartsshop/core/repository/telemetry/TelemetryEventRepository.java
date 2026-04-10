package com.nulp.mobilepartsshop.core.repository.telemetry;

import com.nulp.mobilepartsshop.core.entity.telemetry.TelemetryEvent;
import com.nulp.mobilepartsshop.core.repository.telemetry.projection.RevenueProjection;
import com.nulp.mobilepartsshop.core.repository.telemetry.projection.StaffEfficiencyProjection;
import com.nulp.mobilepartsshop.core.repository.telemetry.projection.UnmetDemandProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TelemetryEventRepository extends JpaRepository<TelemetryEvent, Long> {

    /**
     * Calculates total revenue and order count for a specific period.
     * Uses MySQL JSON extraction to get the 'totalAmount' from the payload.
     */
    @Query(value = """
            SELECT 
                COUNT(id) AS totalOrders, 
                SUM(JSON_EXTRACT(payload, '$.totalAmount')) AS totalRevenue
            FROM telemetry_events 
            WHERE event_type = 'ORDER_CREATED' 
            AND client_timestamp >= :startDate
            """, nativeQuery = true)
    RevenueProjection calculateRevenueSince(@Param("startDate") LocalDateTime startDate);

    /**
     * Calculates the average processing time based on staff finishing orders.
     */
    @Query(value = """
            SELECT 
                COUNT(id) AS totalProcessedOrders, 
                AVG(JSON_EXTRACT(payload, '$.processingTimeSeconds')) AS averageProcessingTimeSeconds
            FROM telemetry_events 
            WHERE event_type = 'ORDER_PROCESSING_FINISHED' 
            AND client_timestamp >= :startDate
            """, nativeQuery = true)
    StaffEfficiencyProjection calculateStaffEfficiencySince(@Param("startDate") LocalDateTime startDate);

    /**
     * Identifies the most frequent searches that yielded zero results.
     * Essential for inventory planning.
     */
    @Query(value = """
            SELECT 
                payload AS searchCriteria, 
                COUNT(id) AS searchCount
            FROM telemetry_events 
            WHERE event_type IN ('SEARCH_BY_DEVICE_RESULT_EMPTY', 'SEARCH_BY_CRITERIA_RESULT_EMPTY')
            AND client_timestamp >= :startDate
            GROUP BY payload
            ORDER BY searchCount DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<UnmetDemandProjection> getTopUnmetDemandSince(
            @Param("startDate") LocalDateTime startDate,
            @Param("limit") int limit
    );
}
