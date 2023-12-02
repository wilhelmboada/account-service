package com.company.accountservice.repository.converter;

import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.model.Phone;
import com.company.accountservice.repository.data.AccountEntity;
import com.company.accountservice.repository.data.PhoneEntity;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountConverterTest {

    @Test
    void testToEntity() {
        // Arrange
        Account account = buildAccount();

        // Act
        AccountEntity accountEntity = AccountConverter.toEntity(account);

        // Assert
        assertEquals(account.getAccountId(), accountEntity.getAccountId());
        assertEquals(account.getName(), accountEntity.getName());
        assertEquals(account.getEmail(), accountEntity.getEmail());
        assertEquals(account.getPassword(), accountEntity.getPassword());
        assertEquals(account.getCreated(), accountEntity.getCreated());
        assertEquals(account.getLastLogin(), accountEntity.getLastLogin());
        assertEquals(account.isActive(), accountEntity.isActive());
        assertEquals(account.getPhones().size(), accountEntity.getPhones().size());
    }

    @Test
    void testToModel() {
        // Arrange
        AccountEntity accountEntity = buildAccountEntity();

        // Act
        Account account = AccountConverter.toModel(accountEntity);

        // Assert
        assertEquals(accountEntity.getAccountId(), account.getAccountId());
        assertEquals(accountEntity.getName(), account.getName());
        assertEquals(accountEntity.getEmail(), account.getEmail());
        assertEquals(accountEntity.getPassword(), account.getPassword());
        assertEquals(accountEntity.getCreated(), account.getCreated());
        assertEquals(accountEntity.getLastLogin(), account.getLastLogin());
        assertEquals(accountEntity.isActive(), account.isActive());
        assertEquals(accountEntity.getPhones().size(), account.getPhones().size());
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

