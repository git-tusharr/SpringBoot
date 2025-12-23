package com.example.util;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PhoneService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public void sendOtp(String toPhone, String otp) {

        Twilio.init(accountSid, authToken);

        Message.creator(
                new PhoneNumber(toPhone),
                new PhoneNumber(twilioPhoneNumber),
                "Your OTP is: " + otp
        ).create();

        System.out.println("OTP sent successfully to " + toPhone);
    }
   
}
