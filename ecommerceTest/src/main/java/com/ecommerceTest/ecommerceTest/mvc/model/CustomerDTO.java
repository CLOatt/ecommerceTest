package com.ecommerceTest.ecommerceTest.mvc.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDTO {

    private Long id;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private String codiceFiscale;
}