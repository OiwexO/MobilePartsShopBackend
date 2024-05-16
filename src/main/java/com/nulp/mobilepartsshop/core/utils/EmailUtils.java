package com.nulp.mobilepartsshop.core.utils;

import com.nulp.mobilepartsshop.exception.email.InvalidEmailTemplateException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmailUtils {

    private static final String TEMPLATES_PATH = "classpath:templates/";

    private static final String TEMPLATE_GREETING_CUSTOMER_EMAIL = "greeting_customer_email.html";

    private static final String TEMPLATE_ORDER_ASSIGNED_STAFF_EMAIL = "order_assigned_staff_email.html";

    private static final String TEMPLATE_ORDER_DELIVERED_CUSTOMER_EMAIL = "order_delivered_customer_email.html";

    private static final String PLACEHOLDER_RECIPIENT_NAME = "[RecipientName]";

    private static final String PLACEHOLDER_ORDER_ID = "[OrderId]";

    private static final String TAG_TITLE_START = "<title>";

    private static final String TAG_TITLE_END = "</title>";

    public static String getGreetingCustomerEmailTemplate(ResourceLoader resourceLoader, String recipientName) throws IOException {
        return getEmailTemplate(resourceLoader, TEMPLATE_GREETING_CUSTOMER_EMAIL, recipientName);
    }

    public static String getOrderAssignedStaffEmail(ResourceLoader resourceLoader, String recipientName, Long orderId) throws IOException {
        String emailTemplate = getEmailTemplate(resourceLoader, TEMPLATE_ORDER_ASSIGNED_STAFF_EMAIL, recipientName);
        return replacePlaceholder(emailTemplate, PLACEHOLDER_ORDER_ID, orderId.toString());
    }

    public static String getOrderDeliveredCustomerEmail(ResourceLoader resourceLoader, String recipientName, Long orderId) throws IOException {
        String emailTemplate = getEmailTemplate(resourceLoader, TEMPLATE_ORDER_DELIVERED_CUSTOMER_EMAIL, recipientName);
        return replacePlaceholder(emailTemplate, PLACEHOLDER_ORDER_ID, orderId.toString());
    }

    public static String getSubject(String emailTemplate) throws InvalidEmailTemplateException {
        int startIndex = emailTemplate.indexOf(TAG_TITLE_START);
        if (startIndex == -1) {
            throw new InvalidEmailTemplateException();
        }
        startIndex += TAG_TITLE_START.length();
        int endIndex = emailTemplate.indexOf(TAG_TITLE_END, startIndex);
        if (endIndex == -1) {
            throw new InvalidEmailTemplateException();
        }
        return emailTemplate.substring(startIndex, endIndex);
    }


    private static String getEmailTemplate(ResourceLoader resourceLoader, String templateName, String recipientName) throws IOException {
        Resource resource = resourceLoader.getResource(TEMPLATES_PATH + templateName);
        String emailTemplate = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        return replacePlaceholder(emailTemplate, PLACEHOLDER_RECIPIENT_NAME, recipientName);

    }

    private static String replacePlaceholder(String emailTemplate, String placeHolder, String value) {
        return emailTemplate.replace(placeHolder, value);
    }

}
