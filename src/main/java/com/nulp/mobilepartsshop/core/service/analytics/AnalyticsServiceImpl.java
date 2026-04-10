package com.nulp.mobilepartsshop.core.service.analytics;

import com.nulp.mobilepartsshop.api.v1.analytics.dto.AnalyticsDashboardResponse;
import com.nulp.mobilepartsshop.api.v1.analytics.service.AnalyticsService;
import com.nulp.mobilepartsshop.core.repository.telemetry.TelemetryEventRepository;
import com.nulp.mobilepartsshop.core.repository.telemetry.projection.RevenueProjection;
import com.nulp.mobilepartsshop.core.repository.telemetry.projection.StaffEfficiencyProjection;
import com.nulp.mobilepartsshop.core.repository.telemetry.projection.UnmetDemandProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final TelemetryEventRepository telemetryRepository;

    private static final int DASHBOARD_PERIOD_DAYS = 30;
    private static final int UNMET_DEMAND_LIMIT = 5;

    @Override
    @Transactional(readOnly = true)
    public AnalyticsDashboardResponse getMonthlyDashboard() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(DASHBOARD_PERIOD_DAYS);

        // 1. Fetch raw projections from the database
        RevenueProjection revenue = telemetryRepository.calculateRevenueSince(startDate);
        StaffEfficiencyProjection staff = telemetryRepository.calculateStaffEfficiencySince(startDate);
        List<UnmetDemandProjection> unmetDemand = telemetryRepository.getTopUnmetDemandSince(startDate, UNMET_DEMAND_LIMIT);

        // 2. Map projections to the clean response DTO
        return mapToDashboardResponse(revenue, staff, unmetDemand);
    }

    /**
     * Maps database projections to the response DTO, safely handling potential SQL nulls.
     * SQL aggregations like SUM() or AVG() return NULL if no rows match the WHERE clause.
     */
    private AnalyticsDashboardResponse mapToDashboardResponse(
            RevenueProjection revenue,
            StaffEfficiencyProjection staff,
            List<UnmetDemandProjection> unmetDemand
    ) {
        // Safe mapping for Financial Metrics
        Integer totalOrders = (revenue != null && revenue.getTotalOrders() != null)
                ? revenue.getTotalOrders() : 0;
        Double totalRevenue = (revenue != null && revenue.getTotalRevenue() != null)
                ? revenue.getTotalRevenue() : 0.0;

        // Safe mapping for Staff Efficiency Metrics
        Integer processedOrders = (staff != null && staff.getTotalProcessedOrders() != null)
                ? staff.getTotalProcessedOrders() : 0;
        Double avgTime = (staff != null && staff.getAverageProcessingTimeSeconds() != null)
                ? staff.getAverageProcessingTimeSeconds() : 0.0;

        // Safe mapping for Unmet Demand List
        List<AnalyticsDashboardResponse.UnmetDemandItem> demandItems = unmetDemand.stream()
                .map(item -> AnalyticsDashboardResponse.UnmetDemandItem.builder()
                        .searchCriteria(item.getSearchCriteria())
                        .searchCount(item.getSearchCount() != null ? item.getSearchCount() : 0)
                        .build())
                .collect(Collectors.toList());

        // Build and return the final response
        return AnalyticsDashboardResponse.builder()
                .financialMetrics(AnalyticsDashboardResponse.FinancialMetrics.builder()
                        .totalOrders(totalOrders)
                        .totalRevenue(totalRevenue)
                        .build())
                .staffMetrics(AnalyticsDashboardResponse.StaffMetrics.builder()
                        .totalProcessedOrders(processedOrders)
                        .averageProcessingTimeSeconds(avgTime)
                        .build())
                .unmetDemand(demandItems)
                .build();
    }
}
