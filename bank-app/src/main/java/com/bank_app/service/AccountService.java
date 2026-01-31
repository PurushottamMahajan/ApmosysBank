package com.bank_app.service;

import com.bank_app.dto.AccountRequestDTO;
import com.bank_app.dto.AccountResponseDTO;
import com.bank_app.entity.Account;
import com.bank_app.entity.Customer;
import com.bank_app.exception.ResourceNotFoundException;
import com.bank_app.repository.AccountRepository;
import com.bank_app.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository,
                          CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public AccountResponseDTO createAccount(AccountRequestDTO dto) {

        Customer customer = customerRepository.findById(dto.getCustomerId())
        	    .orElseThrow(() ->
        	        new ResourceNotFoundException("Customer not found"));


        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setBalance(dto.getInitialBalance());
        account.setCustomer(customer);

        Account saved = accountRepository.save(account);

        AccountResponseDTO response = new AccountResponseDTO();
        response.setAccountId(saved.getAccountId());
        response.setAccountNumber(saved.getAccountNumber());
        response.setAccountType(saved.getAccountType());
        response.setBalance(saved.getBalance());

        return response;
    }

    private String generateAccountNumber() {
        return "AC" + (100000 + new Random().nextInt(900000));
    }
}
