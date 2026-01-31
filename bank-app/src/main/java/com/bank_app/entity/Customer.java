package com.bank_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private LocalDate dob;

    private String gender;

    private String maritalStatus;

    private String nationality;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String mobileNumber;

    @Column(length = 12, unique = true)
    private String aadharNumber;
}
