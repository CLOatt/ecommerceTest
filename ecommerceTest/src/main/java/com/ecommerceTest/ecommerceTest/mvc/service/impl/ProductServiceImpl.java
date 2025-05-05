package com.ecommerceTest.ecommerceTest.mvc.service.impl;

import com.ecommerceTest.ecommerceTest.mapper.ProductMapper;
import com.ecommerceTest.ecommerceTest.mvc.entity.Product;
import com.ecommerceTest.ecommerceTest.mvc.model.ProductDTO;
import com.ecommerceTest.ecommerceTest.mvc.repository.ProductRepository;
import com.ecommerceTest.ecommerceTest.mvc.service.ProductService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductMapper productMapper;


	@Override
	public ResponseEcommerce addNewProduct(ProductDTO product) {

		if (checkProdotto(product)) {
			Product productEntity = productMapper.toEntity(product);
			productRepository.save(productEntity);
			return new ResponseEcommerce(HttpStatus.OK, "nuovo prodotto aggiunto :", product);

		} else {
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "non è possibile creare un Prodotto vuoto.", null);
		}

	}

	@Override
	public ResponseEcommerce deleteProduct(ProductDTO prodotto) {
		if (checkProdotto(prodotto)) {
			Product productEntity = productMapper.toEntity(prodotto);
			productRepository.delete(productEntity);

			return new ResponseEcommerce(HttpStatus.OK, "Prodotto eliminato correttamente:", prodotto);
		} else {
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "non è possibile rimuovere un Prodotto vuoto.", null);
		}

	}

	@Override
	public ProductDTO getProductByCode(String codiceProdotto) {
	    if (!codiceProdotto.isBlank()) {
	        Optional<Product> prodottoEntity = productRepository.findByCodice(codiceProdotto);
	        if (prodottoEntity.isPresent()) {
	            return productMapper.toDTO(prodottoEntity.get());
	        }
	    }
	    return null;
	}

	@Override
	public ResponseEcommerce getAllProducts() {

		List<Product> prodotti = productRepository.findAll();
		if (!prodotti.isEmpty()) {
			return new ResponseEcommerce(HttpStatus.OK, "Prodotti:", prodotti);
		} else {
			return new ResponseEcommerce(HttpStatus.NOT_FOUND, "non sono presenti prodotti", null);
		}
	}

	@Override
	public ProductDTO updateProduct(ProductDTO prodotto, int amount) {
		if (amount > prodotto.getStock()) {
		return null;}

		if (checkProdotto(prodotto)) {
			prodotto.setStock(prodotto.getStock() - amount);
			Product productEntity = productMapper.toEntity(prodotto);
			productRepository.save(productEntity);
			return prodotto;
		} else {
			return null;}
	}

	private boolean checkProdotto(ProductDTO prodotto) {
		if (prodotto == null) {
			return false;
		}
		if (prodotto.getCodice() == null || prodotto.getCodice().isBlank()) {
			return false;
		}
		if (prodotto.getNome() == null || prodotto.getNome().isBlank()) {
			return false;
		}
		if (prodotto.getStock() <= 0) {
			return false;
		}
		return true;
	}
}
