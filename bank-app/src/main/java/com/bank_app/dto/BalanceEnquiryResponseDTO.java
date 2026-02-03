package com.bank_app.dto;

import java.math.BigDecimal;

public class BalanceEnquiryResponseDTO {

    private String accountNumber;
    private BigDecimal balance;

    public BalanceEnquiryResponseDTO() {
    }

    public BalanceEnquiryResponseDTO(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
