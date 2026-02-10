package com.bank_app.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponseDTO {

    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private List<TransactionResponseDTO> recentTransactions;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<TransactionResponseDTO> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<TransactionResponseDTO> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
}
