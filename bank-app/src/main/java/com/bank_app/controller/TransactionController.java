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

    // 🔹 DEBIT / CREDIT
    @PostMapping
    public TransactionResponseDTO performTransaction(
            @Valid @RequestBody TransactionRequestDTO requestDTO) {

        return transactionService.performTransaction(requestDTO);
    }

    // 🔹 ALL TRANSACTIONS
    @GetMapping("/account/{accountNumber}")
    public List<TransactionResponseDTO> getAllTransactions(
            @PathVariable String accountNumber) {

        return transactionService.getAllTransactions(accountNumber);
    }

    // 🔹 STATEMENT BETWEEN DATES
    @GetMapping("/account/{accountNumber}/statement")
    public List<TransactionResponseDTO> getStatementBetweenDates(
            @PathVariable String accountNumber,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fromDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime toDate) {

        return transactionService.getStatementBetweenDates(
                accountNumber, fromDate, toDate);
    }
}
