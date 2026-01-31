package com.bank_app.controller;

import com.bank_app.dto.AccountRequestDTO;
import com.bank_app.dto.AccountResponseDTO;
import com.bank_app.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("*")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponseDTO createAccount(
            @Valid @RequestBody AccountRequestDTO requestDTO) {
        return accountService.createAccount(requestDTO);
    }

}
