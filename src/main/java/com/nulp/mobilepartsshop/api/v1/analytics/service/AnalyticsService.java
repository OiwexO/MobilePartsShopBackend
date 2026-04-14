package com.nulp.mobilepartsshop.api.v1.analytics.service;

import com.nulp.mobilepartsshop.api.v1.analytics.dto.AnalyticsDashboardResponse;

/**
 * Service responsible for aggregating and processing business analytics data.
 */
public interface AnalyticsService {

    /**
     * Generates a comprehensive dashboard report for the recent period (e.g., last 30 days).
     *
     * @return AnalyticsDashboardResponse containing revenue, staff efficiency, and unmet demand.
     */
    AnalyticsDashboardResponse getDashboard(int days);
}
