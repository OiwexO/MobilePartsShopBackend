package com.nulp.mobilepartsshop.core.service.email;

public interface EmailService {

    void sendEmail(String recipientEmail, String recipientName, String templateName);

    void sendGreetingCustomerEmail(String recipientEmail, String recipientName);

    void sendOrderAssignedStaffEmail(String recipientEmail, String recipientName, Long orderId);

    void sendOrderDeliveredCustomerEmail(String recipientEmail, String recipientName, Long orderId);
}
