package com.bank_app.controller;

import com.bank_app.dto.AccountCreationRequestDTO;
import com.bank_app.dto.AccountResponseDTO;
import com.bank_app.dto.BalanceEnquiryResponseDTO;
import com.bank_app.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 🔹 CREATE ACCOUNT USING CIF (DTO → DTO)
    @PostMapping
    public AccountResponseDTO createAccount(
            @RequestBody AccountCreationRequestDTO requestDTO) {

        return accountService.createAccount(requestDTO);
    }

    // 🔹 BALANCE ENQUIRY
    @GetMapping("/{accountNumber}/balance")
    public BalanceEnquiryResponseDTO getBalance(
            @PathVariable String accountNumber) {

        return accountService.getBalance(accountNumber);
    }
}
