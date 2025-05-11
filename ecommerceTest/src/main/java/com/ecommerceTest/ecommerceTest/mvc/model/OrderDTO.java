package com.ecommerceTest.ecommerceTest.mvc.model;

import com.ecommerceTest.ecommerceTest.mvc.entity.OrderProduct;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderDTO {
    private Long id;
    private CustomerDTO cliente;
    private Map<String, Integer> productAmount;

    private String orderStatus;
    }