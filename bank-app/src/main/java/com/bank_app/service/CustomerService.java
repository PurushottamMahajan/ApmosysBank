package com.bank_app.service;

import com.bank_app.dto.CustomerRequestDTO;
import com.bank_app.dto.CustomerResponseDTO;
import com.bank_app.entity.Customer;
import com.bank_app.exception.ResourceNotFoundException;
import com.bank_app.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private String generateCif() {
    return "CIF" + System.currentTimeMillis();
    }

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // CREATE CUSTOMER
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {

        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setDob(dto.getDob());
        customer.setGender(dto.getGender());
        customer.setMaritalStatus(dto.getMaritalStatus());
        customer.setNationality(dto.getNationality());
        customer.setEmail(dto.getEmail());
        customer.setMobileNumber(dto.getMobileNumber());
        customer.setAadharNumber(dto.getAadharNumber());

        Customer savedCustomer = customerRepository.save(customer);
        customer.setCifNumber(generateCif());

        return mapToResponseDTO(savedCustomer);
    }

    // GET ALL CUSTOMERS
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // GET CUSTOMER BY ID
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
        		.orElseThrow(() ->
        	    new ResourceNotFoundException("Customer not found with id " + id));


        return mapToResponseDTO(customer);
    }

    // 🔁 ENTITY → RESPONSE DTO (PRIVATE HELPER)
    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setCustomerId(customer.getCustomerId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setMobileNumber(customer.getMobileNumber());
        return response;
    }
    public CustomerResponseDTO getCustomerByCif(String cifNumber) {
    Customer customer = customerRepository.findByCifNumber(cifNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

    return mapToResponseDTO(customer);
}

}
