package com.ecommerceTest.ecommerceTest.mvc.controller;

import com.ecommerceTest.ecommerceTest.mvc.model.ProductDTO;
import com.ecommerceTest.ecommerceTest.mvc.service.ProductService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;


	@RequestMapping(value = "/prodotto", method = RequestMethod.POST)
	public ResponseEntity<ResponseEcommerce> getProdotto(@RequestBody String codiceProdotto) {
		ResponseEcommerce responseEcommerce;

		if (codiceProdotto == null || codiceProdotto.isBlank()) {
			responseEcommerce = new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Codice prodotto non valido", null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEcommerce);
		}

		ProductDTO prodotto = productService.getProductByCode(codiceProdotto);

		if (prodotto == null) {
			responseEcommerce = new ResponseEcommerce(HttpStatus.NOT_FOUND, "Prodotto non trovato", null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseEcommerce);
		}
		responseEcommerce = new ResponseEcommerce(HttpStatus.OK, "Prodotto trovato", prodotto);
		return ResponseEntity.status(HttpStatus.OK).body(responseEcommerce);

	}

	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public ResponseEntity<ResponseEcommerce> getAllProducts() {
		ResponseEcommerce responseEcommerce = productService.getAllProducts();
		if (HttpStatus.NOT_FOUND.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseEcommerce);

		}

		return ResponseEntity.status(HttpStatus.OK).body(responseEcommerce);

	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
	public ResponseEntity<ResponseEcommerce> deleteProduct(@RequestBody ProductDTO prodotto) {

		ResponseEcommerce responseEcommerce = productService.deleteProduct(prodotto);
		if (HttpStatus.NOT_FOUND.equals(responseEcommerce.getStatus())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseEcommerce);

		}

		return ResponseEntity.status(HttpStatus.OK).body(responseEcommerce);

	}


}
