package com.ecommerceTest.ecommerceTest.mvc.service.impl;

import com.ecommerceTest.ecommerceTest.mapper.CustomerMapper;
import com.ecommerceTest.ecommerceTest.mvc.entity.Customer;
import com.ecommerceTest.ecommerceTest.mvc.model.CustomerDTO;
import com.ecommerceTest.ecommerceTest.mvc.repository.CustomerRepository;
import com.ecommerceTest.ecommerceTest.mvc.service.CustomerService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerMapper customerMapper;

	@Override
	public ResponseEcommerce getallClienti() {

		List<Customer> customer = customerRepository.findAll();
		List<CustomerDTO> clientiDTO = customerMapper.toDTOs(customer);

		if(!customer.isEmpty()){
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "non sono presenti Clienti ", null);
		}


		return new ResponseEcommerce(HttpStatus.OK, "Clienti :", clientiDTO);

	}

	@Override
	public ResponseEcommerce createNewClient(CustomerDTO cliente) {

		if(cliente!= null){
			Customer customerEntity = customerMapper.toEntity(cliente);
			customerRepository.save(customerEntity);
			return new ResponseEcommerce(HttpStatus.OK, "cliente inserito correttamente:", cliente);

		}else {
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "inserimento Cliente fallito, non può essere inserito un cliente senza valori", null);
		}

	}


}
