package com.bank_app.service;

import com.bank_app.dto.DashboardResponseDTO;
import com.bank_app.dto.TransactionResponseDTO;
import com.bank_app.entity.Account;
import com.bank_app.exception.ResourceNotFoundException;
import com.bank_app.repository.AccountRepository;
import com.bank_app.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public DashboardService(AccountRepository accountRepository,
                            TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public DashboardResponseDTO getDashboard(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found"));

        // 🔹 Last 5 transactions
        List<TransactionResponseDTO> recentTransactions =
                transactionRepository
                        .findTop5ByAccountOrderByTransactionDateDesc(account)
                        .stream()
                        .map(t -> {
                            TransactionResponseDTO dto = new TransactionResponseDTO();
                            dto.setTransactionId(t.getTransactionId());
                            dto.setTransactionRef(t.getTransactionRef());
                            dto.setTransactionType(t.getTransactionType());
                            dto.setAmount(t.getAmount());
                            dto.setBalanceAfterTransaction(t.getBalanceAfterTransaction());
                            dto.setTransactionDate(t.getTransactionDate());
                            return dto;
                        })
                        .toList();

        DashboardResponseDTO response = new DashboardResponseDTO();
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType());
        response.setBalance(account.getBalance());
        response.setRecentTransactions(recentTransactions);

        return response;
    }
}
