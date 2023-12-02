package com.company.accountservice.controller.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final PasswordEncoder passwordEncoder;

    public EncryptionService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
