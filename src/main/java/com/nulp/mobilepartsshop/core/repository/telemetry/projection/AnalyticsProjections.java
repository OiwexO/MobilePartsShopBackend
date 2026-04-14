package com.nulp.mobilepartsshop.core.repository.telemetry.projection;

public interface AnalyticsProjections {

    interface FinancialProjection {
        Integer getTotalOrders();
        Double getTotalRevenue();
        Integer getCanceledOrders();
    }

    interface SessionProjection {
        Double getAvgCustomerSession();
        Double getAvgStaffSession();
    }

    interface CartMetricsProjection {
        Integer getItemsAdded();
        Integer getItemsRemoved();
    }

    interface PartCountProjection {
        Long getPartId();
        Integer getEventCount();
    }

    interface UnmetDemandProjection {
        String getSearchCriteria();
        Integer getSearchCount();
    }
}
