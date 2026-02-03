package com.bank_app.controller;

import com.bank_app.dto.CustomerRequestDTO;
import com.bank_app.dto.CustomerResponseDTO;
import com.bank_app.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 🔹 CREATE CUSTOMER (GENERATES CIF)
    @PostMapping
    public CustomerResponseDTO createCustomer(
            @Valid @RequestBody CustomerRequestDTO requestDTO) {

        return customerService.createCustomer(requestDTO);
    }

    // 🔹 FETCH CUSTOMER BY CIF (ACCOUNT CREATION SCREEN)
    @GetMapping("/cif/{cifNumber}")
    public CustomerResponseDTO getCustomerByCif(
            @PathVariable String cifNumber) {

        return customerService.getCustomerByCif(cifNumber);
    }
}
