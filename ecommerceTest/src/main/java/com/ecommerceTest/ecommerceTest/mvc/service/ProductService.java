package com.ecommerceTest.ecommerceTest.mvc.service;

import com.ecommerceTest.ecommerceTest.mvc.model.ProductDTO;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;

public interface ProductService {

	// Metodi per la gestione dei prodotti
	public ResponseEcommerce addNewProduct(ProductDTO prodotto);
	public  ResponseEcommerce deleteProduct(ProductDTO prodotto);
	public ProductDTO getProductByCode(String codiceProdotto);
	public  ResponseEcommerce getAllProducts();
	public ProductDTO updateProduct(ProductDTO prodotto, int amount);

	// Metodi per la gestione delle categorie
//	public void aggiungiCategoria(Categoria categoria);
//	public void rimuoviCategoria(int id);
//	public Categoria getCategoria(int id);
//	public List<Categoria> getTutteLeCategorie();
//	public void aggiornaCategoria(Categoria categoria);
}
