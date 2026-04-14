package com.nulp.mobilepartsshop.core.service.analytics;

import com.nulp.mobilepartsshop.api.v1.analytics.dto.AnalyticsDashboardResponse;
import com.nulp.mobilepartsshop.api.v1.analytics.dto.AnalyticsDashboardResponse.*;
import com.nulp.mobilepartsshop.api.v1.analytics.service.AnalyticsService;
import com.nulp.mobilepartsshop.core.repository.telemetry.TelemetryEventRepository;
import com.nulp.mobilepartsshop.core.repository.telemetry.projection.AnalyticsProjections.*;
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

    private static final int TOP_LIST_LIMIT = 5;

    // Ensure these match the exact strings sent by your Android application configuration
    private static final String CLIENT_APP_SOURCE = "customer_application";
    private static final String STAFF_APP_SOURCE = "staff_application";

    @Override
    @Transactional(readOnly = true)
    public AnalyticsDashboardResponse getDashboard(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);

        FinancialProjection financial = telemetryRepository.getFinancialMetricsSince(startDate);
        SessionProjection sessions = telemetryRepository.getSessionMetricsSince(startDate, CLIENT_APP_SOURCE, STAFF_APP_SOURCE);
        CartMetricsProjection cart = telemetryRepository.getCartMetricsSince(startDate);
        Integer errorCount = telemetryRepository.countAppErrorsSince(startDate);

        List<TopViewedPartProjection> topParts = telemetryRepository.getTopViewedPartsSince(startDate, TOP_LIST_LIMIT);
        List<UnmetDemandProjection> unmetDemand = telemetryRepository.getTopUnmetDemandSince(startDate, TOP_LIST_LIMIT);

        return buildDashboardResponse(financial, sessions, cart, errorCount, topParts, unmetDemand);
    }

    private AnalyticsDashboardResponse buildDashboardResponse(
            FinancialProjection financial,
            SessionProjection sessions,
            CartMetricsProjection cart,
            Integer errorCount,
            List<TopViewedPartProjection> topParts,
            List<UnmetDemandProjection> unmetDemand
    ) {
        // Map Top Parts
        List<TopViewedPart> partList = topParts.stream()
                .map(p -> TopViewedPart.builder()
                        .partId(p.getPartId())
                        .viewCount(p.getViewCount() != null ? p.getViewCount() : 0)
                        .build())
                .collect(Collectors.toList());

        // Map Unmet Demand
        List<UnmetDemandItem> demandList = unmetDemand.stream()
                .map(d -> UnmetDemandItem.builder()
                        .searchCriteria(d.getSearchCriteria())
                        .searchCount(d.getSearchCount() != null ? d.getSearchCount() : 0)
                        .build())
                .collect(Collectors.toList());

        return AnalyticsDashboardResponse.builder()
                .financialMetrics(FinancialMetrics.builder()
                        .totalOrders(financial != null && financial.getTotalOrders() != null ? financial.getTotalOrders() : 0)
                        .totalRevenue(financial != null && financial.getTotalRevenue() != null ? financial.getTotalRevenue() : 0.0)
                        .canceledOrders(financial != null && financial.getCanceledOrders() != null ? financial.getCanceledOrders() : 0)
                        .build())
                .engagementMetrics(EngagementMetrics.builder()
                        .avgCustomerSessionSeconds(sessions != null && sessions.getAvgCustomerSession() != null ? sessions.getAvgCustomerSession() : 0.0)
                        .avgStaffSessionSeconds(sessions != null && sessions.getAvgStaffSession() != null ? sessions.getAvgStaffSession() : 0.0)
                        .build())
                .cartMetrics(CartMetrics.builder()
                        .itemsAdded(cart != null && cart.getItemsAdded() != null ? cart.getItemsAdded() : 0)
                        .itemsRemoved(cart != null && cart.getItemsRemoved() != null ? cart.getItemsRemoved() : 0)
                        .build())
                .systemHealth(SystemHealth.builder()
                        .appErrorCount(errorCount != null ? errorCount : 0)
                        .build())
                .topViewedParts(partList)
                .unmetDemand(demandList)
                .build();
    }
}