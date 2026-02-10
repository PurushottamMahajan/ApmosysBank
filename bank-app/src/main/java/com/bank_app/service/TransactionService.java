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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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

    // 🔹 DEBIT / CREDIT
    public TransactionResponseDTO performTransaction(TransactionRequestDTO dto) {

        Account account = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if ("DEBIT".equalsIgnoreCase(dto.getTransactionType())
                && account.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        BigDecimal updatedBalance =
                "DEBIT".equalsIgnoreCase(dto.getTransactionType())
                        ? account.getBalance().subtract(dto.getAmount())
                        : account.getBalance().add(dto.getAmount());

        account.setBalance(updatedBalance);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setAmount(dto.getAmount());
        transaction.setBalanceAfterTransaction(updatedBalance);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionRef(generateTransactionRef());

        transactionRepository.save(transaction);
        accountRepository.save(account);

        return mapToDTO(transaction);
    }

    // 🔹 ALL TRANSACTIONS
    public List<TransactionResponseDTO> getAllTransactions(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return transactionRepository.findByAccount(account)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔹 STATEMENT BETWEEN DATES
    public List<TransactionResponseDTO> getStatementBetweenDates(
            String accountNumber,
            LocalDateTime from,
            LocalDateTime to) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return transactionRepository
                .findByAccountAndTransactionDateBetween(account, from, to)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔁 ENTITY → DTO
    private TransactionResponseDTO mapToDTO(Transaction t) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(t.getTransactionId());
        dto.setTransactionRef(t.getTransactionRef());
        dto.setTransactionType(t.getTransactionType());
        dto.setAmount(t.getAmount());
        dto.setBalanceAfterTransaction(t.getBalanceAfterTransaction());
        dto.setTransactionDate(t.getTransactionDate());
        return dto;
    }

    // 🔐 Transaction reference generator
    private String generateTransactionRef() {
        return "TXN-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}
