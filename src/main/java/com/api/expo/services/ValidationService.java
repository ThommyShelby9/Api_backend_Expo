package com.api.expo.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.api.expo.models.User;
import com.api.expo.models.Validation;
import com.api.expo.repository.UserRepository;
import com.api.expo.repository.ValidationRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class ValidationService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private ValidationRepository validationRepository;
    private MailSenderService mailSender;
    private UserRepository userRepository;

    public User save(User user) {
        Validation validation = new Validation();
        User savedUser = userRepository.save(user);
        validation.setUser(savedUser);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        validation.setExpire(expiration);
        String code = generateRandomCode(8);
        validation.setCode(code);
        this.validationRepository.save(validation);
        this.mailSender.sendMail(validation);
        return savedUser;
    }

    public Validation readCode(String code) {
        return validationRepository.findByCode(code);
    }

    private String generateRandomCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }
        return code.toString();
    }
}
