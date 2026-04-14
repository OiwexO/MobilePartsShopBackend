package com.nulp.mobilepartsshop.api.v1.analytics.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.analytics.dto.AnalyticsDashboardResponse;
import com.nulp.mobilepartsshop.api.v1.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AnalyticsController.MAPPING)
@RequiredArgsConstructor
public class AnalyticsController {

    public static final String MAPPING = ApiConstants.ANALYTICS_MAPPING_V1 + "/analytics";

    private static final int DASHBOARD_PERIOD_WEEK = 7;
    private static final int DASHBOARD_PERIOD_MONTH = 30;
    private static final int DASHBOARD_PERIOD_QUARTER = 90;

    private final AnalyticsService analyticsService;

    /**
     * Retrieves the business analytics dashboard summary.
     *
     * @param days The period in days to analyze (default is 30).
     */
    @GetMapping("/dashboard")
    public ResponseEntity<AnalyticsDashboardResponse> getDashboardSummary(
            @RequestParam(defaultValue = "30") int days
    ) {
        if (days != DASHBOARD_PERIOD_WEEK && days != DASHBOARD_PERIOD_MONTH && days != DASHBOARD_PERIOD_QUARTER) {
            return ResponseEntity.badRequest().build();
        }
        AnalyticsDashboardResponse response = analyticsService.getDashboard(days);
        return ResponseEntity.ok(response);
    }
}