package com.company.accountservice.controller;

import com.company.accountservice.controller.dto.AccountDTO;
import com.company.accountservice.controller.dto.AccountResponseDTO;
import com.company.accountservice.controller.factory.AccountFactory;
import com.company.accountservice.controller.security.EncryptionService;
import com.company.accountservice.controller.security.JwtUtil;
import com.company.accountservice.domain.model.Account;
import com.company.accountservice.domain.usecase.AccountUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.function.UnaryOperator;

@RestController
public class AccountController {

    private final AccountUseCase accountUseCase;
    private final EncryptionService encryptionService;
    private final JwtUtil jwtUtil;

    public AccountController(
            AccountUseCase accountUseCase,
            EncryptionService encryptionService,
            JwtUtil jwtUtil) {
        this.accountUseCase = accountUseCase;
        this.encryptionService = encryptionService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("sign-up")
    public ResponseEntity<AccountResponseDTO> singUp(@Valid @RequestBody AccountDTO accountDTO) {
        UnaryOperator<String> encryptFunction = encryptionService::encryptPassword;
        Account account = AccountFactory.toModel(accountDTO, encryptFunction);
        Account accountSaved = accountUseCase.createAccount(account);
        return new ResponseEntity<>(AccountFactory.toDTO(accountSaved, jwtUtil.generateToken(accountSaved.getEmail())), HttpStatus.CREATED);
    }

    @GetMapping("login")
    public ResponseEntity<AccountResponseDTO> login(@RequestAttribute("user") String user) {
        Account account = accountUseCase.getAccount(user);
        AccountResponseDTO dto = AccountFactory.toDTO(account, user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
