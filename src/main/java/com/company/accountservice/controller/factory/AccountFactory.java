package com.company.accountservice.controller.factory;

import com.company.accountservice.controller.dto.AccountDTO;
import com.company.accountservice.controller.dto.AccountResponseDTO;
import com.company.accountservice.controller.dto.PhoneDTO;
import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.model.Phone;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class AccountFactory {

    private AccountFactory() {

    }

    public static Account toModel(AccountDTO accountDTO, UnaryOperator<String> encryptFunction) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        List<Phone> phones = Optional
                .ofNullable(accountDTO.getPhones())
                .map(list -> list.stream()
                        .map(AccountFactory::toModel)
                        .collect(Collectors.toList()))
                .orElse(List.of());
        return Account
                .builder()
                .accountId(UUID.randomUUID().toString())
                .name(accountDTO.getName())
                .email(accountDTO.getEmail())
                .password(encryptFunction.apply(accountDTO.getPassword()))
                .created(zonedDateTime)
                .lastLogin(zonedDateTime)
                .isActive(true)
                .phones(phones)
                .build();
    }

    public static AccountResponseDTO toDTO(Account account, String token) {
        List<PhoneDTO> phones = Optional
                .ofNullable(account.getPhones())
                .map(list -> list.stream()
                        .map(AccountFactory::toDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());

        return AccountResponseDTO
                .builder()
                .id(account.getAccountId())
                .name(account.getName())
                .email(account.getEmail())
                .created(account.getCreated())
                .lastLogin(account.getLastLogin())
                .token(token)
                .isActive(account.isActive())
                .phones(phones)
                .build();
    }

    private static Phone toModel(PhoneDTO phone) {
        return Phone.builder()
                .number(phone.getNumber())
                .cityCode(phone.getCityCode())
                .countryCode(phone.getCountryCode())
                .build();
    }

    private static PhoneDTO toDTO(Phone phone) {
        return PhoneDTO.builder()
                .number(phone.getNumber())
                .cityCode(phone.getCityCode())
                .countryCode(phone.getCountryCode())
                .build();
    }
}
