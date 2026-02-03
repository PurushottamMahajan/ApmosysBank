package com.bank_app.dto;

import lombok.Data;

@Data
public class AccountCreationRequestDTO {

    private String cifNumber;
    private String accountType; // SAVINGS / CURRENT
}
