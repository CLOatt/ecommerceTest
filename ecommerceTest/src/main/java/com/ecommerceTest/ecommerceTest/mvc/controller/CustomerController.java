package com.ecommerceTest.ecommerceTest.mvc.controller;

import com.ecommerceTest.ecommerceTest.mvc.model.CustomerDTO;
import com.ecommerceTest.ecommerceTest.mvc.service.CustomerService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {


	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/getAllCustomer", method = RequestMethod.GET)
	public ResponseEntity<ResponseEcommerce> getAllCustomer() {
		ResponseEcommerce responseEcommerce = customerService.getallClienti();

		if ( HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);

	}


	@GetMapping("/customersPaginated")
	public ResponseEntity<ResponseEcommerce> getAllClientiPaginati(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		ResponseEcommerce response = customerService.getAllClientiPaginati(pageable);

		return new ResponseEntity<>(response, response.getStatus());
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

	@RequestMapping(value = "/deleteCustomer/{customerId}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseEcommerce> deleteCustomer(@PathVariable Long customerId) {
		ResponseEcommerce responseEcommerce = customerService.deleteCustomerById(customerId);
		if (HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);
	}


}
