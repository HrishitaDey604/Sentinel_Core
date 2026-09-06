package com.sentinel.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAlertEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("Alert email successfully sent to: " + to);
        } catch (Exception e) {
            // Catches SMTP exceptions, quota limits, and network dropouts gracefully
            System.err.println("Skipped email notification (SMTP limit reached or error): " + e.getMessage());
        }
    }
}