package com.bank_app.repository;

import com.bank_app.entity.Account;
import com.bank_app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccount(Account account);

    List<Transaction> findByAccountAndTransactionDateBetween(
            Account account,
            LocalDateTime from,
            LocalDateTime to
    );
    List<Transaction> findTop5ByAccountOrderByTransactionDateDesc(Account account);

}
