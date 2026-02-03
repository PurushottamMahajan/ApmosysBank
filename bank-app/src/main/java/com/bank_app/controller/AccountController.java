package com.bank_app.controller;

import com.bank_app.dto.AccountCreationRequestDTO;
import com.bank_app.dto.BalanceEnquiryResponseDTO;
import com.bank_app.entity.Account;
import com.bank_app.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 🔹 CREATE ACCOUNT USING CIF
    @PostMapping
    public Account createAccount(
            @RequestBody AccountCreationRequestDTO requestDTO) {

        return accountService.createAccount(requestDTO);
    }

    // 🔹 BALANCE ENQUIRY
    @GetMapping("/{accountNumber}/balance")
    public BalanceEnquiryResponseDTO getBalance(
            @PathVariable String accountNumber) {

        BigDecimal balance = accountService.getBalance(accountNumber);

        BalanceEnquiryResponseDTO response = new BalanceEnquiryResponseDTO();
        response.setAccountNumber(accountNumber);
        response.setBalance(balance);

        return response;
    }
}
