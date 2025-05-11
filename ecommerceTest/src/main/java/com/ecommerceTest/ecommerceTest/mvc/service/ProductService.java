package com.ecommerceTest.ecommerceTest.mvc.service;

import com.ecommerceTest.ecommerceTest.mvc.model.ProductDTO;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;

public interface ProductService {

	public ResponseEcommerce addNewProduct(ProductDTO prodotto);
	public  ResponseEcommerce deleteProduct(ProductDTO prodotto);
	public ProductDTO getProductByCode(String codiceProdotto);
	public  ResponseEcommerce getAllProducts();
	public ProductDTO updateProduct(ProductDTO prodotto, int amount);
	public ResponseEcommerce createNewProduct(ProductDTO prodotto);
	public ResponseEcommerce getAllProductsPaginated(int page, int size);

	}
