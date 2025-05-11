package com.ecommerceTest.ecommerceTest.mvc.service;

import com.ecommerceTest.ecommerceTest.mvc.model.CustomerDTO;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import org.springframework.data.domain.Pageable;
public interface CustomerService {

	public ResponseEcommerce getallClienti();

	public ResponseEcommerce createNewClient(CustomerDTO cliente);

	public ResponseEcommerce deleteCustomerById(Long customerId);

	public  ResponseEcommerce getAllClientiPaginati(Pageable pagenable);
}
