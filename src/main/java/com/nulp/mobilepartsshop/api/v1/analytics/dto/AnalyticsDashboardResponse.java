package com.nulp.mobilepartsshop.api.v1.analytics.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object representing the comprehensive business analytics dashboard.
 */
@Data
@Builder
public class AnalyticsDashboardResponse {

    private final FinancialMetrics financialMetrics;

    private final StaffMetrics staffMetrics;

    private final List<UnmetDemandItem> unmetDemand;

    @Data
    @Builder
    public static class FinancialMetrics {
        private final Integer totalOrders;
        private final Double totalRevenue;
    }

    @Data
    @Builder
    public static class StaffMetrics {
        private final Integer totalProcessedOrders;
        private final Double averageProcessingTimeSeconds;
    }

    @Data
    @Builder
    public static class UnmetDemandItem {
        private final String searchCriteria;
        private final Integer searchCount;
    }
}
