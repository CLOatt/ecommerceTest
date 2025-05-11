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

	@RequestMapping(value = "/getOrderByCustomer/{customerFiscalCode}", method = RequestMethod.GET)
	public ResponseEntity<ResponseEcommerce> getAllOrderByCustomer(@PathVariable String customerFiscalCode) {
		ResponseEcommerce responseEcommerce = orderService.getOrderByCustomer(customerFiscalCode);
		if (HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);
	}


	@RequestMapping(value = "/createNewOrder", method = RequestMethod.POST)
	public ResponseEntity<ResponseEcommerce> createNewOrder(@RequestBody CreateOrderRequestDTO createOrderRequestDTO) {
		// Controlla se il customer è nullo
		if (createOrderRequestDTO.getOrder().getCliente() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Il cliente è obbligatorio", null));
		}

		// Esegui la logica per creare un nuovo ordine
		ResponseEcommerce responseEcommerce = orderService.createNewOrdine(createOrderRequestDTO.getOrder());

		// Se la risposta è BAD_REQUEST, restituisci errore
		if (HttpStatus.BAD_REQUEST.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}

		// Restituisci il risultato
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

	@RequestMapping(value = "/getAllOrdersPaginated", method = RequestMethod.GET)
	public ResponseEntity<ResponseEcommerce> getAllOrdersPaginated(@RequestParam int page, @RequestParam int size) {
		ResponseEcommerce responseEcommerce = orderService.getAllOrdersPaginated(page, size);
		return ResponseEntity.status(responseEcommerce.getStatus()).body(responseEcommerce);
	}

}
