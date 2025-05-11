package com.ecommerceTest.ecommerceTest.mvc.model;

import lombok.Data;

@Data
public class ProductDTO {

    private Long productId;
    private String code;
    private String name;
    private int stock;
}