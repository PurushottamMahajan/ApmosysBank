package com.bank_app.service;

import com.bank_app.dto.AccountCreationRequestDTO;
import com.bank_app.dto.AccountResponseDTO;
import com.bank_app.dto.BalanceEnquiryResponseDTO;
import com.bank_app.entity.Account;
import com.bank_app.entity.Customer;
import com.bank_app.exception.ResourceNotFoundException;
import com.bank_app.repository.AccountRepository;
import com.bank_app.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;

    public AccountService(AccountRepository accountRepo,
                          CustomerRepository customerRepo) {
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
    }

    // =========================
    // CREATE ACCOUNT
    // =========================
    public AccountResponseDTO createAccount(AccountCreationRequestDTO dto) {

        Customer customer = customerRepo.findByCifNumber(dto.getCifNumber())
            .orElseThrow(() -> new ResourceNotFoundException("Invalid CIF"));

    Account account = new Account();
    account.setAccountNumber("AC" + System.currentTimeMillis());
    account.setAccountType(dto.getAccountType());
    account.setBalance(BigDecimal.ZERO);
    account.setCustomer(customer);

    Account saved = accountRepo.save(account);

    AccountResponseDTO response = new AccountResponseDTO();
    response.setAccountNumber(saved.getAccountNumber());
    response.setAccountType(saved.getAccountType());
    response.setBalance(saved.getBalance());
    response.setCifNumber(customer.getCifNumber());

    return response;

    }

    // =========================
    // BALANCE ENQUIRY
    // =========================
    public BalanceEnquiryResponseDTO getBalance(String accountNumber) {

Account account = accountRepo.findByAccountNumber(accountNumber)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Account not found"));

    return new BalanceEnquiryResponseDTO(
            account.getAccountNumber(),
            account.getBalance()
    );
    }

    // =========================
    // ENTITY → DTO
    // =========================
    private AccountResponseDTO mapToResponseDTO(Account account) {

        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setCifNumber(account.getCustomer().getCifNumber());

        return dto;
    }

    // =========================
    // ACCOUNT NUMBER GENERATOR
    // =========================
    private String generateAccountNumber() {
        return "AC" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}
