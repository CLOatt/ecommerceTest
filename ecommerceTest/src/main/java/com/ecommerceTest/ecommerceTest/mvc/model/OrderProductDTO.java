package com.ecommerceTest.ecommerceTest.mvc.model;


import lombok.Data;

@Data
public class OrderProductDTO {

	private OrderDTO order;

	private ProductDTO product;


	private int amount;

}
