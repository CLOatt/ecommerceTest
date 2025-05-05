package com.ecommerceTest.ecommerceTest.mvc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "CLIENTE")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    
    @Column(name = "NOME", nullable = false)
    private String name;
    
    @Column(name = "SURNAME", nullable = false)
    private String surname;
    
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name ="FISCAL_CODE", unique = true, nullable = false)
    private String fiscalCode;

    @Column(name = "EMAIL", nullable = false)
    private String email;
    
    @JoinColumn(name = "prodotto_id", nullable = false)
    private Order order;

}