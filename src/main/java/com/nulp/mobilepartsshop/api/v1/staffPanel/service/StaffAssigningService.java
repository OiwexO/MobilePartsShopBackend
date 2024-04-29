package com.nulp.mobilepartsshop.api.v1.staffPanel.service;

import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.exception.staffPanel.NoAvailableStaffException;

public interface StaffAssigningService {

    void assignFreeStaffToOrder(Order order) throws NoAvailableStaffException;
}
