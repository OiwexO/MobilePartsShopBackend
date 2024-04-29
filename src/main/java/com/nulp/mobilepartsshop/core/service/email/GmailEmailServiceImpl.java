package com.nulp.mobilepartsshop.core.service.email;

import com.nulp.mobilepartsshop.core.utils.EmailUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GmailEmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private final ResourceLoader resourceLoader;

    @Value("${spring.mail.username}")
    private String EMAIL_FROM;

    @Override
    public void sendEmail(String recipientEmail, String recipientName, String emailTemplate) {
        try {
            String subject = EmailUtils.getSubject(emailTemplate);
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setFrom(EMAIL_FROM);
            helper.setSubject(subject);
            helper.setText(emailTemplate, true);
            emailSender.send(message);
        } catch (Exception e) {
            //TODO handle exception
        }
    }

    @Override
    public void sendGreetingCustomerEmail(String recipientEmail, String recipientName) {
        String emailTemplate;
        try {
            emailTemplate = EmailUtils.getGreetingCustomerEmailTemplate(resourceLoader, recipientName);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO handle exception
        }
        sendEmail(recipientEmail, recipientName, emailTemplate);
    }

    @Override
    public void sendOrderAssignedStaffEmail(String recipientEmail, String recipientName, Long orderId) {
        String emailTemplate;
        try {
            emailTemplate = EmailUtils.getOrderAssignedStaffEmail(resourceLoader, recipientName, orderId);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO handle exception
        }
        sendEmail(recipientEmail, recipientName, emailTemplate);
    }

    @Override
    public void sendOrderDeliveredCustomerEmail(String recipientEmail, String recipientName, Long orderId) {
        String emailTemplate;
        try {
            emailTemplate = EmailUtils.getOrderDeliveredCustomerEmail(resourceLoader, recipientName, orderId);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO handle exception
        }
        sendEmail(recipientEmail, recipientName, emailTemplate);
    }
}
