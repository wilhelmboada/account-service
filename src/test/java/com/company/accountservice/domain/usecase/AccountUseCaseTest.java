package com.company.accountservice.domain.usecase;

import com.company.accountservice.domain.error.BusinessError;
import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.model.Phone;
import com.company.accountservice.domain.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountUseCaseTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountUseCase accountUseCase;

    public AccountUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount_Success() {
        // Arrange
        Account account = buildAccount();
        when(accountService.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(accountService.save(account)).thenReturn(account);

        // Act
        Account createdAccount = accountUseCase.createAccount(account);

        // Assert
        assertNotNull(createdAccount);
        assertEquals("test@test.com", createdAccount.getEmail());
        verify(accountService, times(1)).findByEmail("test@test.com");
        verify(accountService, times(1)).save(account);
    }

    @Test
    void testCreateAccount_AccountAlreadyExists() {
        // Arrange
        Account existingAccount = buildAccount();
        when(accountService.findByEmail("test@test.com")).thenReturn(Optional.of(existingAccount));

        // Act and Assert
        assertThrows(BusinessError.class, () -> accountUseCase.createAccount(existingAccount));
        verify(accountService, times(1)).findByEmail("test@test.com");
        verify(accountService, never()).save(existingAccount);
    }

    @Test
    void testGetAccount_Success() {
        // Arrange
        Account existingAccount = buildAccount();
        when(accountService.findByEmail("test@test.com")).thenReturn(Optional.of(existingAccount));

        // Act
        Account retrievedAccount = accountUseCase.getAccount("test@test.com");

        // Assert
        assertNotNull(retrievedAccount);
        assertEquals("test@test.com", retrievedAccount.getEmail());
        verify(accountService, times(1)).findByEmail("test@test.com");
    }

    @Test
    void testGetAccount_AccountNotFound() {
        // Arrange
        when(accountService.findByEmail("test@test.com")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BusinessError.class, () -> accountUseCase.getAccount("test@test.com"));
        verify(accountService, times(1)).findByEmail("test@test.com");
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
                .email("test@test.com")
                .password("encrypted_password")
                .phones(List.of(phone))
                .build();
    }
}
