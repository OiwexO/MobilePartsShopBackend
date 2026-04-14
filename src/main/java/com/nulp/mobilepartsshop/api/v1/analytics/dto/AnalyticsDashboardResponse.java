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
    private final EngagementMetrics engagementMetrics;
    private final CartMetrics cartMetrics;
    private final SystemHealth systemHealth;
    private final List<TopViewedPart> topViewedParts;
    private final List<UnmetDemandItem> unmetDemand;

    @Data
    @Builder
    public static class FinancialMetrics {
        private final Integer totalOrders;
        private final Double totalRevenue;
        private final Integer canceledOrders;
    }

    @Data
    @Builder
    public static class EngagementMetrics {
        private final Double avgCustomerSessionSeconds;
        private final Double avgStaffSessionSeconds;
    }

    @Data
    @Builder
    public static class CartMetrics {
        private final Integer itemsAdded;
        private final Integer itemsRemoved;
    }

    @Data
    @Builder
    public static class SystemHealth {
        private final Integer appErrorCount;
    }

    @Data
    @Builder
    public static class TopViewedPart {
        private final Long partId;
        private final Integer viewCount;
    }

    @Data
    @Builder
    public static class UnmetDemandItem {
        private final String searchCriteria;
        private final Integer searchCount;
    }
}
