package com.nulp.mobilepartsshop.api.v1.analytics.dto;

import com.nulp.mobilepartsshop.api.v1.part.dto.response.PartResponse; // Import your actual PartResponse
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalyticsDashboardResponse {

    private final FinancialMetrics financialMetrics;
    private final EngagementMetrics engagementMetrics;
    private final CartMetrics cartMetrics;
    private final SystemHealth systemHealth;

    // Updated to hold the full PartResponse
    private final List<PartMetricItem> topViewedParts;
    private final List<PartMetricItem> topPurchasedParts;

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

    /**
     * Universal class to hold any part-related metric (views, purchases, etc.)
     * containing the full part details for the client UI.
     */
    @Data
    @Builder
    public static class PartMetricItem {
        private final PartResponse part;
        private final Integer count;
    }

    @Data
    @Builder
    public static class UnmetDemandItem {
        private final String searchCriteria;
        private final Integer searchCount;
    }
}