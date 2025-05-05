package com.ecommerceTest.ecommerceTest.mvc.model;

import com.ecommerceTest.ecommerceTest.mvc.entity.OrderProduct;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private CustomerDTO cliente;
    private List<OrderProduct> prodotti;
    private String orderStatus;
    }