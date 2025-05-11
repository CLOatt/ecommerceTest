package com.ecommerceTest.ecommerceTest.exceptions;

public class OrderNotFoundException extends RuntimeException {
	public OrderNotFoundException(String message) {
		super(message);
	}
}
