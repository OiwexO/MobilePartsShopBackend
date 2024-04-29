package com.nulp.mobilepartsshop.core.service.email;

public interface EmailService {

    void sendEmail(String to, String subject, String content);

    void sendGreetingEmail(String to);

    void sendOrderAssignedStaffEmail(String to);

    void sendOrderDeliveredCustomerEmail(String to);
}
