package com.bank_app.service;

import com.bank_app.dto.CustomerRequestDTO;
import com.bank_app.dto.CustomerResponseDTO;
import com.bank_app.entity.Customer;
import com.bank_app.exception.ResourceNotFoundException;
import com.bank_app.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // =========================
    // CREATE CUSTOMER
    // =========================
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {

        Customer customer = new Customer();

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setMobileNumber(dto.getMobileNumber());
        customer.setDob(dto.getDob());
        customer.setGender(dto.getGender());
        customer.setMaritalStatus(dto.getMaritalStatus());
        customer.setNationality(dto.getNationality());
        customer.setAadharNumber(dto.getAadharNumber());

        // 🔐 Generate CIF in backend
        customer.setCifNumber(generateCif());

        Customer saved = customerRepository.save(customer);

        return mapToResponseDTO(saved);
    }

    // =========================
    // GET ALL CUSTOMERS
    // =========================
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // =========================
    // GET CUSTOMER BY ID
    // =========================
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id " + id)
                );

        return mapToResponseDTO(customer);
    }

    // =========================
    // GET CUSTOMER BY CIF
    // =========================
    public CustomerResponseDTO getCustomerByCif(String cifNumber) {
        Customer customer = customerRepository.findByCifNumber(cifNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with CIF " + cifNumber)
                );

        return mapToResponseDTO(customer);
    }

    // =========================
    // ENTITY → RESPONSE DTO
    // =========================
    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        CustomerResponseDTO response = new CustomerResponseDTO();

        response.setCustomerId(customer.getCustomerId()); // ✅ CONSISTENT
        response.setCifNumber(customer.getCifNumber());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setMobileNumber(customer.getMobileNumber());

        return response;
    }

    // =========================
    // CIF GENERATOR
    // =========================
    private String generateCif() {
        return "CIF" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10)
                .toUpperCase();
    }
}
