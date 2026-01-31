package com.bank_app.service;

import com.bank_app.dto.TransactionRequestDTO;
import com.bank_app.dto.TransactionResponseDTO;
import com.bank_app.entity.Account;
import com.bank_app.entity.Transaction;
import com.bank_app.exception.InsufficientBalanceException;
import com.bank_app.exception.ResourceNotFoundException;
import com.bank_app.repository.AccountRepository;
import com.bank_app.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    // ✅ DEBIT / CREDIT TRANSACTION
    public void performTransaction(TransactionRequestDTO dto) {

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id: " + dto.getAccountId()));

        // DEBIT
        if (dto.getTransactionType().equalsIgnoreCase("DEBIT")) {

            if (account.getBalance().compareTo(dto.getAmount()) < 0) {
                throw new InsufficientBalanceException("Insufficient balance");
            }

            account.setBalance(account.getBalance().subtract(dto.getAmount()));
        }
        // CREDIT
        else if (dto.getTransactionType().equalsIgnoreCase("CREDIT")) {

            account.setBalance(account.getBalance().add(dto.getAmount()));
        }
        else {
            throw new RuntimeException("Invalid transaction type");
        }

        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setAmount(dto.getAmount());
        transaction.setBalanceAfterTransaction(account.getBalance());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        accountRepository.save(account);
        transactionRepository.save(transaction);
    }

    // ✅ ALL TRANSACTIONS OF ACCOUNT
    public List<TransactionResponseDTO> getAllTransactions(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id: " + accountId));

        return transactionRepository.findByAccountAccountId(account.getAccountId())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ STATEMENT BETWEEN DATES
    public List<TransactionResponseDTO> getStatementBetweenDates(
            Long accountId,
            LocalDateTime from,
            LocalDateTime to) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id: " + accountId));

        return transactionRepository
                .findByAccountAccountIdAndTransactionDateBetween(
                        account.getAccountId(), from, to)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔁 ENTITY → DTO
    private TransactionResponseDTO mapToDTO(Transaction t) {

        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(t.getTransactionId());
        dto.setTransactionType(t.getTransactionType());
        dto.setAmount(t.getAmount());
        dto.setBalanceAfterTransaction(t.getBalanceAfterTransaction());
        dto.setTransactionDate(t.getTransactionDate());
        return dto;
    }
}
