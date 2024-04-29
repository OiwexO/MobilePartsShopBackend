package com.nulp.mobilepartsshop.core.service.staffPanel;

import com.nulp.mobilepartsshop.api.v1.staffPanel.service.StaffAssigningService;
import com.nulp.mobilepartsshop.core.entity.order.Order;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import com.nulp.mobilepartsshop.exception.staffPanel.NoAvailableStaffException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffAssigningServiceImpl implements StaffAssigningService {

    private final UserRepository userRepository;

    @Override
    public void assignFreeStaffToOrder(Order order) throws NoAvailableStaffException {
        List<User> freeStaffs = userRepository.findStaffUsersSortedByAssignedOrdersSize();
        if (freeStaffs.isEmpty()) {
            throw new NoAvailableStaffException();
        }
        User freeStaff = freeStaffs.get(0);
        List<Order> assignedOrders = freeStaff.getAssignedOrders();
        assignedOrders.add(order);
        freeStaff.setAssignedOrders(assignedOrders);
        order.setStaffId(freeStaff.getId());
        userRepository.save(freeStaff);
        //TODO send email to staff
    }
}
