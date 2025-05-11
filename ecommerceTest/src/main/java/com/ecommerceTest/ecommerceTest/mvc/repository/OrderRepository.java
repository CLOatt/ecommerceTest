package com.ecommerceTest.ecommerceTest.mvc.repository;

import com.ecommerceTest.ecommerceTest.mvc.entity.Customer;
import com.ecommerceTest.ecommerceTest.mvc.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByCustomer(Customer customer);


	@Query("SELECT o FROM Order o JOIN o.customer c WHERE c.fiscalCode = :fiscalCode")
	List<Order> findOrdersByCustomerFiscalCode(@Param("fiscalCode") String fiscalCode);


}