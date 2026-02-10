package com.bank_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountCreationRequestDTO {

    @NotBlank(message = "CIF number is required")
    private String cifNumber;

    @NotBlank(message = "Account type is required")
    private String accountType; // SAVINGS / CURRENT
}
