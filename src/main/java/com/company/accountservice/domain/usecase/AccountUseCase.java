package com.company.accountservice.domain.usecase;

import com.company.accountservice.domain.error.BusinessError;
import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.service.AccountService;
import org.springframework.stereotype.Service;


@Service
public class AccountUseCase {

    private final AccountService accountService;

    public AccountUseCase(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account createAccount(Account account) {
        if (accountService.findByEmail(account.getEmail()).isPresent()) {
            throw new BusinessError(String.format("Account with email %s has already exist", account.getEmail()));
        } else {
            return accountService.save(account);
        }
    }

    public Account getAccount(String email) {
        return accountService.findByEmail(email)
                .orElseThrow(() -> new BusinessError("Account does not exist"));
    }
}
