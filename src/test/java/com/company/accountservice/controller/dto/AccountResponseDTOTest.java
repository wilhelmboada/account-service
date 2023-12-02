package com.company.accountservice.controller.dto;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountResponseDTOTest {

    @Test
    void testBuilder() {
        // Arrange
        String id = "testId";
        String name = "TestUser";
        String email = "test@example.com";
        ZonedDateTime created = ZonedDateTime.now();
        ZonedDateTime lastLogin = ZonedDateTime.now().minusDays(1);
        String token = "testToken";
        boolean isActive = true;
        List<PhoneDTO> phones = Arrays.asList(
                new PhoneDTO(123, "1", "US"),
                new PhoneDTO( 456, "44", "UK")
        );

        // Act
        AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
                .id(id)
                .name(name)
                .email(email)
                .created(created)
                .lastLogin(lastLogin)
                .token(token)
                .isActive(isActive)
                .phones(phones)
                .build();

        // Assert
        assertNotNull(accountResponseDTO);
        assertEquals(id, accountResponseDTO.getId());
        assertEquals(name, accountResponseDTO.getName());
        assertEquals(email, accountResponseDTO.getEmail());
        assertEquals(created, accountResponseDTO.getCreated());
        assertEquals(lastLogin, accountResponseDTO.getLastLogin());
        assertEquals(token, accountResponseDTO.getToken());
        assertEquals(isActive, accountResponseDTO.isActive());
        assertEquals(phones, accountResponseDTO.getPhones());
    }
}
