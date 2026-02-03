package com.bank_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    // 🔹 Auto-generated & unique
    @Column(unique = true, nullable = false)
    private String accountNumber;

    // SAVINGS / CURRENT
    @Column(nullable = false)
    private String accountType;

    // Always updated via transactions
    @Column(nullable = false)
    private BigDecimal balance;

    // Many accounts can belong to one customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;  
}
