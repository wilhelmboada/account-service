package com.company.accountservice.repository.converter;

import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.model.Phone;
import com.company.accountservice.repository.data.AccountEntity;
import com.company.accountservice.repository.data.PhoneEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AccountConverter {

    private AccountConverter() {

    }

    public static AccountEntity toEntity(Account account) {
        List<PhoneEntity> phones = account
                .getPhones()
                .stream()
                .map(AccountConverter::toEntity)
                .collect(Collectors.toList());
        return AccountEntity.builder()
                .accountId(account.getAccountId())
                .name(account.getName())
                .email(account.getEmail())
                .password(account.getPassword())
                .created(account.getCreated())
                .lastLogin(account.getLastLogin())
                .isActive(account.isActive())
                .phones(phones)
                .build();
    }

    public static Account toModel(AccountEntity account) {
        List<Phone> phones = account
                .getPhones()
                .stream()
                .map(AccountConverter::toModel)
                .collect(Collectors.toList());
        return Account.builder()
                .accountId(account.getAccountId())
                .name(account.getName())
                .email(account.getEmail())
                .password(account.getPassword())
                .created(account.getCreated())
                .lastLogin(account.getLastLogin())
                .isActive(account.isActive())
                .phones(phones)
                .build();
    }

    private static PhoneEntity toEntity(Phone phone) {
        return PhoneEntity.builder()
                .number(phone.getNumber())
                .cityCode(phone.getCityCode())
                .countryCode(phone.getCountryCode())
                .build();
    }

    private static Phone toModel(PhoneEntity phone) {
        return Phone.builder()
                .number(phone.getNumber())
                .cityCode(phone.getCityCode())
                .countryCode(phone.getCountryCode())
                .build();
    }

}
