package com.company.accountservice.repository.service;

import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.service.AccountService;
import com.company.accountservice.repository.AccountRepository;
import com.company.accountservice.repository.converter.AccountConverter;
import com.company.accountservice.repository.data.AccountEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = accountRepository.save(AccountConverter.toEntity(account));
        return AccountConverter.toModel(accountEntity);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .map(AccountConverter::toModel);
    }
}
