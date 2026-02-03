package com.bank_app.repository;

import com.bank_app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // 🔹 NEW: Core banking lookup
    Optional<Account> findByAccountNumber(String accountNumber);
}
