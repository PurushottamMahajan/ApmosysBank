package com.bank_app.repository;

import com.bank_app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountAccountId(Long accountId);

    List<Transaction> findByAccountAccountIdAndTransactionDateBetween(
            Long accountId,
            LocalDateTime fromDate,
            LocalDateTime toDate
    );
}
