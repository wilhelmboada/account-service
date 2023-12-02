package com.company.accountservice.domain.service;

import com.company.accountservice.domain.model.Account;

import java.util.Optional;

public interface AccountService {

    Account save(Account account);

    Optional<Account> findByEmail(String email);
}
