package com.ecommerceTest.ecommerceTest.mvc.controller;

import com.ecommerceTest.ecommerceTest.mvc.model.CustomerDTO;
import com.ecommerceTest.ecommerceTest.mvc.service.CustomerService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {


	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ResponseEntity<ResponseEcommerce> getAllCustomer() {
		ResponseEcommerce responseEcommerce = customerService.getallClienti();

		if ( HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);



	}


	@RequestMapping(value = "/createNewCustomer", method = RequestMethod.POST)
	public ResponseEntity<ResponseEcommerce> createNewCustomer(@RequestBody CustomerDTO cliente) {
		ResponseEcommerce responseEcommerce = null;
		try {
			responseEcommerce  = customerService.createNewClient(cliente);
			return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);

		} catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
