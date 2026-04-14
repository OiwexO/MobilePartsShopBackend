package com.nulp.mobilepartsshop.core.repository.telemetry;

import com.nulp.mobilepartsshop.core.entity.telemetry.TelemetryEvent;
import com.nulp.mobilepartsshop.core.repository.telemetry.projection.AnalyticsProjections.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TelemetryEventRepository extends JpaRepository<TelemetryEvent, Long> {

    @Query(value = """
            SELECT 
                SUM(CASE WHEN event_type = 'ORDER_CREATED' THEN 1 ELSE 0 END) AS totalOrders,
                SUM(CASE WHEN event_type = 'ORDER_CREATED' THEN JSON_EXTRACT(payload, '$.totalAmount') ELSE 0 END) AS totalRevenue,
                SUM(CASE WHEN event_type = 'ORDER_CANCELED' THEN 1 ELSE 0 END) AS canceledOrders
            FROM telemetry_events 
            WHERE event_type IN ('ORDER_CREATED', 'ORDER_CANCELED')
            AND client_timestamp >= :startDate
            """, nativeQuery = true)
    FinancialProjection getFinancialMetricsSince(@Param("startDate") LocalDateTime startDate);

    @Query(value = """
            SELECT 
                AVG(CASE WHEN source_app = :clientAppName THEN JSON_EXTRACT(payload, '$.sessionDurationSeconds') ELSE NULL END) AS avgCustomerSession,
                AVG(CASE WHEN source_app = :staffAppName THEN JSON_EXTRACT(payload, '$.sessionDurationSeconds') ELSE NULL END) AS avgStaffSession
            FROM telemetry_events 
            WHERE event_type = 'APP_SESSION_ENDED'
            AND client_timestamp >= :startDate
            """, nativeQuery = true)
    SessionProjection getSessionMetricsSince(
            @Param("startDate") LocalDateTime startDate,
            @Param("clientAppName") String clientAppName,
            @Param("staffAppName") String staffAppName
    );

    @Query(value = """
            SELECT 
                SUM(CASE WHEN event_type = 'CART_ITEM_ADDED' THEN 1 ELSE 0 END) AS itemsAdded,
                SUM(CASE WHEN event_type = 'CART_ITEM_REMOVED' THEN 1 ELSE 0 END) AS itemsRemoved
            FROM telemetry_events 
            WHERE event_type IN ('CART_ITEM_ADDED', 'CART_ITEM_REMOVED')
            AND client_timestamp >= :startDate
            """, nativeQuery = true)
    CartMetricsProjection getCartMetricsSince(@Param("startDate") LocalDateTime startDate);

    @Query(value = """
            SELECT 
                JSON_EXTRACT(payload, '$.partId') AS partId, 
                COUNT(id) AS viewCount
            FROM telemetry_events 
            WHERE event_type = 'PART_VIEWED'
            AND client_timestamp >= :startDate
            GROUP BY partId
            ORDER BY viewCount DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<TopViewedPartProjection> getTopViewedPartsSince(
            @Param("startDate") LocalDateTime startDate,
            @Param("limit") int limit
    );

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

    @Query(value = """
            SELECT COUNT(id) FROM telemetry_events 
            WHERE event_type = 'APP_ERROR' 
            AND client_timestamp >= :startDate
            """, nativeQuery = true)
    Integer countAppErrorsSince(@Param("startDate") LocalDateTime startDate);
}
