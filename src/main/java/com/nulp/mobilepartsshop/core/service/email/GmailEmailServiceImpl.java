package com.nulp.mobilepartsshop.core.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GmailEmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String to, String subject, String content) {

    }

    @Override
    public void sendGreetingEmail(String to) {

    }

    @Override
    public void sendOrderAssignedStaffEmail(String to) {

    }

    @Override
    public void sendOrderDeliveredCustomerEmail(String to) {

    }
}
