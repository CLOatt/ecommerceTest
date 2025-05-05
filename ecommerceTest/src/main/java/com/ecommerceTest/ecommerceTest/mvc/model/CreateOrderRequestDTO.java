package com.ecommerceTest.ecommerceTest.mvc.model;

import lombok.Data;

import java.util.Map;

@Data
public class CreateOrderRequestDTO
{
	private OrderDTO order;
	private CustomerDTO customer;
	private Map<String, Integer> productAmount;
}