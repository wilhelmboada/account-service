package com.company.accountservice.controller.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private static final String SECRET_KEY = "secret-key";
    private static final String EMAIL = "test@test.com";

    @Mock
    private Jwts jwts;

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil(SECRET_KEY);
    }

    @Test
    void testExtractUser() {
        // Arrange
        String token = jwtUtil.generateToken(EMAIL);

        // Act
        String extractedUser = jwtUtil.extractUser(token);

        // Assert
        assertEquals(EMAIL, extractedUser);
    }

    @Test
    void testExtractClaims() {
        // Arrange
        String token = jwtUtil.generateToken(EMAIL);

        // Act
        Claims claims = jwtUtil.extractAllClaims(token);

        // Assert
        assertEquals(EMAIL, claims.getSubject());
    }

    @Test
    void testGenerateToken() {
        // Act
        String generatedToken = jwtUtil.generateToken(EMAIL);

        // Assert
        assertNotNull(generatedToken);
    }

    @Test
    void testValidateToken_ValidToken() {
        // Arrange
        String token = jwtUtil.generateToken(EMAIL);

        // Act
        boolean isValid = jwtUtil.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_ExpiredToken() {
        // Arrange
        String expiredToken = Jwts
                .builder()
                .setSubject(EMAIL)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000)) // Set to 1 second ago
                .setExpiration(new Date(System.currentTimeMillis() - 500)) // Set to 0.5 seconds ago
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // Act
        boolean isValid = jwtUtil.validateToken(expiredToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_InvalidToken() {
        // Arrange
        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3NkLmNvbSIsImlhdCI6MTcwMTMxMjM4OCwiZXhwIjoxNzAxMzEyOTg4fQ.e8lAzb6vYmaxyOG-kDdcb0iSe1ESCsupicsA4ju2PO8";

        // Act
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

}

