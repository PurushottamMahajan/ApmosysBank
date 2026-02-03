package com.bank_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private String transactionType; // DEBIT / CREDIT

    private BigDecimal amount;

    private BigDecimal balanceAfterTransaction;

    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @Column(unique = true, nullable = false)
    private String transactionRef;

}
