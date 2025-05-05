package com.ecommerceTest.ecommerceTest.mvc.service;

import com.ecommerceTest.ecommerceTest.mvc.model.CustomerDTO;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;

public interface CustomerService {

	public ResponseEcommerce getallClienti();

	public ResponseEcommerce createNewClient(CustomerDTO cliente);
}
