package com.bank_app.controller;

import com.bank_app.dto.TransactionRequestDTO;
import com.bank_app.dto.TransactionResponseDTO;
import com.bank_app.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 🔹 DEBIT / CREDIT TRANSACTION
    @PostMapping
    public String doTransaction(
            @Valid @RequestBody TransactionRequestDTO requestDTO) {

        transactionService.performTransaction(requestDTO);
        return "Transaction Successful";
    }

    // 🔹 ALL TRANSACTIONS OF AN ACCOUNT
    @GetMapping("/account/{accountId}")
    public List<TransactionResponseDTO> getAllTransactions(
            @PathVariable Long accountId) {

        return transactionService.getAllTransactions(accountId);
    }

    // 🔹 STATEMENT BETWEEN DATES
    @GetMapping("/account/{accountId}/statement")
    public List<TransactionResponseDTO> getStatementBetweenDates(
            @PathVariable Long accountId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fromDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime toDate) {

        return transactionService.getStatementBetweenDates(
                accountId, fromDate, toDate);
    }
}
