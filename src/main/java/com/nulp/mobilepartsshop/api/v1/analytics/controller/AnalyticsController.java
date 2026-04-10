package com.nulp.mobilepartsshop.api.v1.analytics.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.analytics.dto.AnalyticsDashboardResponse;
import com.nulp.mobilepartsshop.api.v1.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AnalyticsController.MAPPING)
@RequiredArgsConstructor
public class AnalyticsController {

    public static final String MAPPING = ApiConstants.ANALYTICS_MAPPING_V1 + "/analytics";

    private final AnalyticsService analyticsService;

    /**
     * Retrieves the business analytics dashboard summary for the last 30 days.
     * Note: Ensure this endpoint is secured in SecurityConfig to allow only ADMIN or MANAGER roles.
     *
     * @return ResponseEntity containing the aggregated dashboard data.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<AnalyticsDashboardResponse> getDashboardSummary() {
        AnalyticsDashboardResponse response = analyticsService.getMonthlyDashboard();
        return ResponseEntity.ok(response);
    }
}