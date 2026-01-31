package com.bank_app.controller;

import com.bank_app.dto.CustomerRequestDTO;
import com.bank_app.dto.CustomerResponseDTO;
import com.bank_app.service.CustomerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // CREATE CUSTOMER
    @PostMapping
    public CustomerResponseDTO createCustomer(
            @Valid @RequestBody CustomerRequestDTO requestDTO) {
        return customerService.createCustomer(requestDTO);
    }


    // GET ALL CUSTOMERS
    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // GET CUSTOMER BY ID
    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}
