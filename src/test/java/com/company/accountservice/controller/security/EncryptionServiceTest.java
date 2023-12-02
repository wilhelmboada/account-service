package com.company.accountservice.controller.security;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class EncryptionServiceTest {

    private static final String RAW_PASSWORD = "password";
    private static final String ENCRYPTED_PASSWORD = "$2a$10$123456789012345678901uI6y43QcIuXe6jTSB9dRU4L1A7zO0vE";

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EncryptionService encryptionService;

    public EncryptionServiceTest() {
        MockitoAnnotations.openMocks(this);
        encryptionService = new EncryptionService(passwordEncoder);
    }

    @Test
    void testEncryptPassword() {
        // Arrange
        when(passwordEncoder.encode(RAW_PASSWORD)).thenReturn(ENCRYPTED_PASSWORD);

        // Act
        String encryptedPassword = encryptionService.encryptPassword(RAW_PASSWORD);

        // Assert
        assertNotNull(encryptedPassword);
    }
}
