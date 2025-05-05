package com.ecommerceTest.ecommerceTest.mvc.model;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String codice;
    private String nome;
    private int stock;
}