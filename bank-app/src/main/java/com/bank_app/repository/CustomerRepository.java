package com.bank_app.repository;

import com.bank_app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 🔹 Used during customer creation (validation)
    boolean existsByEmail(String email);

    boolean existsByAadharNumber(String aadharNumber);

    // 🔹 NEW: Used for Account Creation (CIF-based flow)
    Optional<Customer> findByCifNumber(String cifNumber);
}
