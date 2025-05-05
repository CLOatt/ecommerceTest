package com.ecommerceTest.ecommerceTest.mvc.repository;


import com.ecommerceTest.ecommerceTest.mvc.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


	public Customer findByFiscalCode(String fiscalCode);
}