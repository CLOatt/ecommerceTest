package com.ecommerceTest.ecommerceTest.mvc.service.impl;

import ch.qos.logback.classic.Logger;
import com.ecommerceTest.ecommerceTest.mapper.CustomerMapper;
import com.ecommerceTest.ecommerceTest.mvc.entity.Customer;
import com.ecommerceTest.ecommerceTest.mvc.entity.Product;
import com.ecommerceTest.ecommerceTest.mvc.model.CustomerDTO;
import com.ecommerceTest.ecommerceTest.mvc.repository.CustomerRepository;
import com.ecommerceTest.ecommerceTest.mvc.service.CustomerService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerMapper customerMapper;


	private static final org.slf4j.Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);



	@Override
	public ResponseEcommerce getallClienti() {


		List<Customer> customer = customerRepository.findAll();

		log.info("trovati {} ",  customer );
		List<CustomerDTO> clientiDTO = customerMapper.toDTOs(customer);


		log.info("trovati DTO {} ",  customer );

		if(customer.isEmpty()){
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "non sono presenti Clienti ", null);
		}


		return new ResponseEcommerce(HttpStatus.OK, "Clienti :", clientiDTO);

	}

	@Override
	public ResponseEcommerce getAllClientiPaginati(Pageable pageable) {
		log.info("Recupero clienti paginati con page: {} e size: {}", pageable.getPageNumber(), pageable.getPageSize());
		// Recupera i clienti in modo paginato
		Page<Customer> customerPage = customerRepository.findAll(pageable);

		log.info("Trovati clienti paginati: {}", customerPage.getContent());
		List<CustomerDTO> clientiDTO = customerMapper.toDTOs(customerPage.getContent());

		if (customerPage.isEmpty()) {
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Non sono presenti clienti nella pagina richiesta", null);
		}
		if (customerPage.getSize() > customerPage.getContent().size()) {
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "La dimensione della pagina è maggiore alla lista di clienti", null);
		}
		return new ResponseEcommerce(HttpStatus.OK, "Clienti paginati:", clientiDTO);
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

	@Override
	public ResponseEcommerce deleteCustomerById(Long customerId) {
		if (customerId!=null) {
			Customer customerEntity = customerRepository.findById(customerId).orElse(null);
			assert customerEntity != null;
			customerRepository.delete(customerEntity);
			CustomerDTO customer = customerMapper.toDTO(customerEntity);
			return new ResponseEcommerce(HttpStatus.OK, "Customer eliminato correttamente:", customer);
		} else {
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "non è possibile rimuovere un Prodotto vuoto.", null);
		}
	}


}
