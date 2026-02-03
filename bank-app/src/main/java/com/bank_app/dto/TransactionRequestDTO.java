package com.bank_app.dto;

import java.math.BigDecimal;

public class TransactionRequestDTO {

    private String accountNumber;
    private String transactionType; // DEBIT / CREDIT
    private BigDecimal amount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
