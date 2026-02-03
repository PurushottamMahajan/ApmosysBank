package com.bank_app.dto;

import lombok.Data;

@Data
public class CustomerResponseDTO {
    private Long customerId;
    private String cifNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
}
