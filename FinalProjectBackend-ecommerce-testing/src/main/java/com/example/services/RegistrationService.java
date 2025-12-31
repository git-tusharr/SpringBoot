package com.example.services;

import com.example.FinalProjectBackendEcommerceTestingApplication;
import com.example.config.RedisConfig;
import com.example.models.User;
import com.example.repository.UserRepository;
import com.example.util.EmailServicereg;
import com.example.util.OTPUtil;
import com.example.util.PhoneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RegistrationService {

    private final FinalProjectBackendEcommerceTestingApplication finalProjectBackendEcommerceTestingApplication;
    private final RedisConfig redisConfig;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServicereg emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    // Temporary OTP storage (kept as-is)
    private final Map<String, String> emailOtpMap = new HashMap<>();
    private final Map<String, String> phoneOtpMap = new HashMap<>();

    RegistrationService(RedisConfig redisConfig,
                        FinalProjectBackendEcommerceTestingApplication finalProjectBackendEcommerceTestingApplication) {
        this.redisConfig = redisConfig;
        this.finalProjectBackendEcommerceTestingApplication = finalProjectBackendEcommerceTestingApplication;
    }

    public void registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("PENDING");

        userRepository.save(user);

        String emailOtp = OTPUtil.generateOTP();
        String phoneOtp = OTPUtil.generateOTP();

        String formattedPhone =
                user.getPhone().startsWith("+") ? user.getPhone() : "+91" + user.getPhone();

        redisTemplate.opsForValue()
                .set("phone_otp:" + formattedPhone, phoneOtp, 15, TimeUnit.MINUTES);

        redisTemplate.opsForValue()
                .set("email_otp:" + user.getEmail(), emailOtp, 15, TimeUnit.MINUTES);

        // ✅ ONLY CHANGE: added 4th parameter (emailOtp)
        emailService.sendEmail(
                user.getEmail(),
                "Verify your email OTP",
                "Your OTP is:",
                emailOtp
        );

        phoneService.sendOtp(formattedPhone, phoneOtp);
    }

    public boolean verifyEmailOTP(String email, String otp) {

        String key = "email_otp:" + email;
        String redisOtp = redisTemplate.opsForValue().get(key);

        if (redisOtp != null && redisOtp.equals(otp)) {
            redisTemplate.delete(key);

            User user = userRepository.findByEmail(email).orElseThrow();
            user.setEmailVerified(true);
            activateIfReady(user); // sets status to ACTIVE if both verified
            userRepository.saveAndFlush(user); // force DB update


            return true;
        }
        return false;
    }

    public boolean verifyPhoneOTP(String phone, String otp) {

        String formattedPhone = phone.startsWith("+") ? phone : "+91" + phone;
        String key = "phone_otp:" + formattedPhone;
        String redisOtp = redisTemplate.opsForValue().get(key);

        if (redisOtp != null && redisOtp.equals(otp)) {
            redisTemplate.delete(key);

            User user = userRepository.findByPhone(formattedPhone).orElseThrow();
            user.setPhoneVerified(true);
            activateIfReady(user);
            userRepository.saveAndFlush(user);


            return true;
        }
        return false;
    }

    private void activateIfReady(User user) {
        if (user.isEmailVerified() && user.isPhoneVerified()) {
            user.setStatus("ACTIVE");
        }
    }
}
