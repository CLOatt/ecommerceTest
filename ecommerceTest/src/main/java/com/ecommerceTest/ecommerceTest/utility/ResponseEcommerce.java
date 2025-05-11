package com.ecommerceTest.ecommerceTest.utility;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseEcommerce {

	private String Message;
	private Object obj;
	private HttpStatus status;

	public ResponseEcommerce(HttpStatus status, String message, Object obj) {
		super();
		Message = message;
		this.obj = obj;
		this.status = status;
	}
}
