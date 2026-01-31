package com.bank_app.repository;

import com.bank_app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Custom methods (optional, for later)
    boolean existsByEmail(String email);

    boolean existsByAadharNumber(String aadharNumber);
}
