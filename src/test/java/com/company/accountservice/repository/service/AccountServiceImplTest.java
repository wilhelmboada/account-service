package com.company.accountservice.repository.service;

import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.model.Phone;
import com.company.accountservice.repository.AccountRepository;
import com.company.accountservice.repository.converter.AccountConverter;
import com.company.accountservice.repository.data.AccountEntity;
import com.company.accountservice.repository.data.PhoneEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    public AccountServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    void testSave() {
        // Arrange
        Account account = buildAccount();
        AccountEntity accountEntity = AccountConverter.toEntity(account);

        when(accountRepository.save(accountEntity)).thenReturn(accountEntity);

        // Act
        Account savedAccount = accountService.save(account);

        // Assert
        assertEquals(account.getEmail(), savedAccount.getEmail());
        verify(accountRepository, times(1)).save(accountEntity);
    }

    @Test
    void testFindByEmail_Exists() {
        // Arrange
        String email = "test@test.com";
        AccountEntity accountEntity = buildAccountEntity();

        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(accountEntity));

        // Act
        Optional<Account> foundAccount = accountService.findByEmail(email);

        // Assert
        assertTrue(foundAccount.isPresent());
        assertEquals(email, foundAccount.get().getEmail());
        verify(accountRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindByEmail_NotExists() {
        // Arrange
        String nonExistingEmail = "test@test.com";

        when(accountRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        // Act
        Optional<Account> foundAccount = accountService.findByEmail(nonExistingEmail);

        // Assert
        assertTrue(foundAccount.isEmpty());
        verify(accountRepository, times(1)).findByEmail(nonExistingEmail);
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

    private AccountEntity buildAccountEntity() {
        PhoneEntity phone = PhoneEntity
                .builder()
                .number(123)
                .cityCode("1")
                .countryCode("1")
                .build();
        return AccountEntity.builder()
                .name("name")
                .email("test@test.com")
                .password("encrypted_password")
                .phones(List.of(phone))
                .build();
    }
}
