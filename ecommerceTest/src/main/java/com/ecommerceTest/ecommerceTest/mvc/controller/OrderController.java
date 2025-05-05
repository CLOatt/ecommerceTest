package com.ecommerceTest.ecommerceTest.mvc.controller;

import com.ecommerceTest.ecommerceTest.mvc.model.CreateOrderRequestDTO;
import com.ecommerceTest.ecommerceTest.mvc.service.OrderService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/getAllOrder", method = RequestMethod.GET)
	public ResponseEntity<ResponseEcommerce> getAllOrder() {
		ResponseEcommerce responseEcommerce = orderService.getAllOrder();
		if (HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);
	}

	@RequestMapping(value = "/getOrderByOwner/{customerFiscalCode}", method = RequestMethod.GET)
	public ResponseEntity<ResponseEcommerce> getAllOrderByCustomer(@PathVariable String customerFiscalCode) {
		ResponseEcommerce responseEcommerce = orderService.getOrderByCustomer(customerFiscalCode);
		if (HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);
	}


	@RequestMapping(value = "/createNewOrder", method = RequestMethod.POST)
	public ResponseEntity<ResponseEcommerce> createNewOrder(@RequestBody CreateOrderRequestDTO createOrderRequestDTO) {
		ResponseEcommerce responseEcommerce = orderService.createNewOrdine(createOrderRequestDTO.getOrder(), createOrderRequestDTO.getCustomer(), createOrderRequestDTO.getProductAmount());
		if (HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);
	}

	@RequestMapping(value = "/deleteOrder/{orderId}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseEcommerce> deleteOrder(@PathVariable Long orderId) {
		ResponseEcommerce responseEcommerce = orderService.deleteOrderById(orderId);
		if (HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);
	}


	@RequestMapping(value = "/updateOrderStatus/{orderId}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseEcommerce> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
		ResponseEcommerce responseEcommerce = orderService.updateOrderStatus(orderId, status);
		if (HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);
	}

}
