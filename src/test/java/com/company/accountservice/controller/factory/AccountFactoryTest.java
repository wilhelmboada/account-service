package com.company.accountservice.controller.factory;

import com.company.accountservice.controller.dto.AccountDTO;
import com.company.accountservice.controller.dto.AccountResponseDTO;
import com.company.accountservice.controller.dto.PhoneDTO;
import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.model.Phone;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountFactoryTest {

    @Test
    void testToModel() {
        // Arrange
        PhoneDTO phone = PhoneDTO.builder()
                .number(123)
                .cityCode("1")
                .countryCode("1")
                .build();
        AccountDTO accountDTO = AccountDTO
                .builder()
                .name("name")
                .email("test@test.com")
                .password("password")
                .phones(List.of(phone))
                .build();
        UnaryOperator<String> encryptFunction = s -> "encrypted_" + s;

        // Act
        Account account = AccountFactory.toModel(accountDTO, encryptFunction);

        // Assert
        assertEquals("name", account.getName());
        assertEquals("test@test.com", account.getEmail());
        assertEquals("encrypted_password", account.getPassword());
        assertEquals(1, account.getPhones().size());
    }

    @Test
    void testToDTO() {
        // Arrange
        Phone phone = Phone.builder().id(1L)
                .number(123)
                .cityCode("1")
                .countryCode("1")
                .build();
        Account account = new Account(
                UUID.randomUUID().toString(),
                "John Doe",
                "john@example.com",
                "encrypted_password",
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                true,
                Collections.singletonList(phone)
        );
        String token = "testToken";

        // Act
        AccountResponseDTO accountResponseDTO = AccountFactory.toDTO(account, token);

        // Assert
        assertEquals(account.getAccountId(), accountResponseDTO.getId());
        assertEquals(account.getName(), accountResponseDTO.getName());
        assertEquals(account.getEmail(), accountResponseDTO.getEmail());
        assertEquals(token, accountResponseDTO.getToken());
        assertEquals(account.isActive(), accountResponseDTO.isActive());
        assertEquals(1, accountResponseDTO.getPhones().size());
    }
}

