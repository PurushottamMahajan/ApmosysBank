package com.bank_app.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountResponseDTO {
    private Long accountId;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
}
