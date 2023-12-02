package com.company.accountservice.repository.data;

import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountEntityTest {

    private final ZonedDateTime NOW = ZonedDateTime.now();

    @Test
    void testValidAccountEntity() {

        // Arrange
        String id = "testId";
        String name = "TestUser";
        String email = "test@example.com";
        String password = "Password123";
        boolean isActive = true;

        AccountEntity accountEntity = AccountEntity.builder()
                .accountId(id)
                .name(name)
                .email(email)
                .password(password)
                .created(NOW)
                .lastLogin(NOW)
                .isActive(true)
                .phones(List.of(new PhoneEntity()))
                .build();

        // Assert
        assertNotNull(accountEntity);
        assertEquals(id, accountEntity.getAccountId());
        assertEquals(name, accountEntity.getName());
        assertEquals(email, accountEntity.getEmail());
        assertEquals(password, accountEntity.getPassword());
        assertEquals(NOW, accountEntity.getCreated());
        assertEquals(NOW, accountEntity.getLastLogin());
        assertEquals(isActive, accountEntity.isActive());
    }

    @Test
    void testValidAccountEntity2() {
        // Arrange
        String id = "testId";
        String name = "TestUser";
        String email = "test@example.com";
        String password = "Password123";

        AccountEntity accountEntity = AccountEntity.builder()
                .accountId(id)
                .name(name)
                .email(email)
                .password(password)
                .created(NOW)
                .lastLogin(NOW)
                .isActive(true)
                .phones(List.of(new PhoneEntity()))
                .build();

        String id2 = "testId";
        String name2 = "TestUser";
        String email2 = "test@example.com";
        String password2 = "Password123";

        accountEntity.setAccountId(id2);
        accountEntity.setName(name2);
        accountEntity.setEmail(email2);
        accountEntity.setPassword(password2);
        accountEntity.setCreated(NOW);
        accountEntity.setLastLogin(NOW);
        accountEntity.setActive(false);

        // Assert
        assertNotNull(accountEntity);
        assertEquals(id2, accountEntity.getAccountId());
        assertEquals(name2, accountEntity.getName());
        assertEquals(email2, accountEntity.getEmail());
        assertEquals(password2, accountEntity.getPassword());
        assertEquals(NOW, accountEntity.getCreated());
        assertEquals(NOW, accountEntity.getLastLogin());
        assertFalse(accountEntity.isActive());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        AccountEntity accountEntity1 = AccountEntity.builder()
                .accountId("testId")
                .name("TestUser")
                .email("test@example.com")
                .password("Password123")
                .created(zonedDateTime)
                .lastLogin(zonedDateTime)
                .isActive(true)
                .phones(List.of(new PhoneEntity()))
                .build();

        AccountEntity accountEntity2 = AccountEntity.builder()
                .accountId("testId")
                .name("TestUser")
                .email("test@example.com")
                .password("Password123")
                .created(zonedDateTime)
                .lastLogin(zonedDateTime)
                .isActive(true)
                .phones(List.of(new PhoneEntity()))
                .build();

        // Act and Assert
        assertThat(accountEntity1).isEqualTo(accountEntity2);
        assertThat(accountEntity1.hashCode()).hasSameHashCodeAs(accountEntity2.hashCode());

        // Change one attribute
        AccountEntity modifiedEntity = accountEntity1.toBuilder().isActive(false).build();

        assertThat(accountEntity1).isNotEqualTo(modifiedEntity);
        assertThat(accountEntity1.hashCode()).isNotEqualTo(modifiedEntity.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        AccountEntity accountEntity = AccountEntity.builder()
                .accountId("testId")
                .name("TestUser")
                .email("test@example.com")
                .password("Password123")
                .created(NOW)
                .lastLogin(NOW)
                .isActive(true)
                .phones(List.of(new PhoneEntity()))
                .build();

        // Act
        String toStringResult = accountEntity.toString();

        // Assert
        assertThat(toStringResult).contains("accountId=testId");
        assertThat(toStringResult).contains("name=TestUser");
        assertThat(toStringResult).contains("email=test@example.com");
        assertThat(toStringResult).contains("password=Password123");
        assertThat(toStringResult).contains("isActive=true");
        assertThat(toStringResult).contains("phones=[PhoneEntity(");
    }

}


