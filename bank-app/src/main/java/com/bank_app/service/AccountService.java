package com.bank_app.service;

import com.bank_app.dto.AccountCreationRequestDTO;
import com.bank_app.entity.Account;
import com.bank_app.entity.Customer;
import com.bank_app.exception.ResourceNotFoundException;
import com.bank_app.repository.AccountRepository;
import com.bank_app.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;

    public AccountService(AccountRepository accountRepo, CustomerRepository customerRepo) {
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
    }

    public Account createAccount(AccountCreationRequestDTO dto) {

        Customer customer = customerRepo
                .findByCifNumber(dto.getCifNumber())
                .orElseThrow(() -> new RuntimeException("Invalid CIF"));

        Account account = new Account();
        account.setAccountNumber("AC" + System.currentTimeMillis());
        account.setAccountType(dto.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setCustomer(customer);

        return accountRepo.save(account);
    }
public BigDecimal getBalance(String accountNumber) {
    Account account = accountRepo.findByAccountNumber(accountNumber)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Account not found"));
    return account.getBalance();
}
}