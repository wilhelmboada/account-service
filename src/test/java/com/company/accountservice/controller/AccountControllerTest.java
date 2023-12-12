package com.company.accountservice.controller;

import com.company.accountservice.controller.dto.AccountDTO;
import com.company.accountservice.controller.dto.AccountResponseDTO;
import com.company.accountservice.controller.dto.PhoneDTO;
import com.company.accountservice.controller.security.EncryptionService;
import com.company.accountservice.controller.security.JwtUtil;
import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.model.Phone;
import com.company.accountservice.domain.usecase.AccountUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountUseCase accountUseCase;

    @Mock
    private EncryptionService encryptionService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignUp() {
        // Arrange
        AccountDTO accountDTO = buildAccountDTO();
        UnaryOperator<String> encryptFunction = s -> "encrypted_" + s;
        when(encryptionService.encryptPassword(any())).thenAnswer(invocation -> encryptFunction.apply(invocation.getArgument(0)));
        when(jwtUtil.generateToken(any())).thenReturn("testToken");
        when(accountUseCase.createAccount(any())).thenReturn(buildAccount());

        // Act
        ResponseEntity<AccountResponseDTO> responseEntity = accountController.singUp(accountDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(accountUseCase, times(1)).createAccount(any(Account.class));
    }

    @Test
    void testLogin() {
        // Arrange
        when(accountUseCase.getAccount("test@test.com")).thenReturn(buildAccount()); // Assuming encryption logic

        // Act
        ResponseEntity<AccountResponseDTO> responseEntity = accountController.login("test@test.com");

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("test@test.com", responseEntity.getBody().getToken());
    }

    private AccountDTO buildAccountDTO() {
        PhoneDTO phoneDTO = PhoneDTO
                .builder()
                .number(123)
                .cityCode("1")
                .countryCode("1")
                .build();
        return AccountDTO
                .builder()
                .name("name")
                .email("test@tessd.com")
                .password("passworD12")
                .phones(List.of(phoneDTO))
                .build();
    }

    private Account buildAccount() {
        Phone phone = Phone
                .builder()
                .number(123)
                .cityCode("1")
                .countryCode("1")
                .build();
        return Account.builder()
                .name("name")
                .email("test@tessd.com")
                .password("encrypted_password")
                .phones(List.of(phone))
                .build();
    }
}

