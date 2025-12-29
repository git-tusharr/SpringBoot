package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicereg {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        
        // Create a more readable email body
        String formattedBody = body + "\n\nYour OTP for verification is: " + otp + 
                               "\n\nPlease use this OTP within the next 15 minutes.";

        // Set the email body (plain text only)
        message.setText(formattedBody); 
        
        // Send the email
        mailSender.send(message);
        
        // Log the OTP and email confirmation (for debugging purposes)
        System.out.println("OTP successfully sent to email: " + to + ". OTP: " + otp);
    }
}
