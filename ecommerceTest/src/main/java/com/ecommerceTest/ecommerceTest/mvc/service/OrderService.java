package com.ecommerceTest.ecommerceTest.mvc.service;

import com.ecommerceTest.ecommerceTest.mvc.model.CustomerDTO;
import com.ecommerceTest.ecommerceTest.mvc.model.OrderDTO;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;

import java.util.Map;

public interface OrderService {

	public ResponseEcommerce getAllOrder();

	public ResponseEcommerce createNewOrdine(OrderDTO order, CustomerDTO customer, Map<String, Integer> productAmount);

	public ResponseEcommerce getOrderByCustomer(String customerFiscalCode);

	public ResponseEcommerce deleteOrderById(Long orderId);

	public ResponseEcommerce updateOrderStatus(Long orderId, String status);

	}
