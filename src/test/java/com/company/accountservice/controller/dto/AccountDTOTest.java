package com.company.accountservice.controller.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AccountDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValidAccountDTO() {
        // Arrange
        AccountDTO accountDTO = AccountDTO.builder()
                .name("TestUser")
                .email("test@example.com")
                .password("Password123")
                .phones(List.of(new PhoneDTO(123, "1", "US")))
                .build();

        // Act
        Set<ConstraintViolation<AccountDTO>> violations = validator.validate(accountDTO);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    void testInvalidEmail() {

        // Arrange
        AccountDTO accountDTO = AccountDTO.builder()
                .name("TestUser")
                .email("invalidEmail")
                .password("Password123")
                .phones(List.of(new PhoneDTO(123, "1", "US")))
                .build();

        // Act
        Set<ConstraintViolation<AccountDTO>> violations = validator.validate(accountDTO);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("is not valid");
    }

    @Test
    void testNullPassword() {
        // Arrange
        AccountDTO accountDTO = AccountDTO.builder()
                .name("TestUser")
                .email("test@example.com")
                .password(null)
                .phones(List.of(new PhoneDTO(123, "1", "US")))
                .build();

        // Act
        Set<ConstraintViolation<AccountDTO>> violations = validator.validate(accountDTO);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("cannot be empty or null");
    }

    @Test
    void testWeakPassword() {
        // Arrange
        AccountDTO accountDTO = AccountDTO.builder()
                .name("TestUser")
                .email("test@example.com")
                .password("weak")
                .phones(List.of(new PhoneDTO(123, "1", "US")))
                .build();

        // Act
        Set<ConstraintViolation<AccountDTO>> violations = validator.validate(accountDTO);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("is not valid");
    }

}

