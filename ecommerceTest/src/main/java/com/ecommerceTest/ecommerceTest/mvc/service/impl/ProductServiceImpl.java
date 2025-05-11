package com.ecommerceTest.ecommerceTest.mvc.service.impl;

import com.ecommerceTest.ecommerceTest.mapper.ProductMapper;
import com.ecommerceTest.ecommerceTest.mvc.entity.Product;
import com.ecommerceTest.ecommerceTest.mvc.model.ProductDTO;
import com.ecommerceTest.ecommerceTest.mvc.repository.ProductRepository;
import com.ecommerceTest.ecommerceTest.mvc.service.ProductService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductMapper productMapper;


	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
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
	        Optional<Product> prodottoEntity = productRepository.findByCode(codiceProdotto);
	        if (prodottoEntity.isPresent()) {
				logger.info("Prodotto trovato: {}", prodottoEntity.get());
	            return productMapper.toDTO(prodottoEntity.get());
	        }
	    }
		logger.error("Prodotto non trovato con codice: {}", codiceProdotto);
	    return null;
	}

	@Override
	public ResponseEcommerce getAllProducts() {

		List<Product> prodotti = productRepository.findAll();
		if (!prodotti.isEmpty()) {
			logger.info("Prodotti trovati: {}", prodotti);
			return new ResponseEcommerce(HttpStatus.OK, "Prodotti:", prodotti);
		} else {
			logger.error("Nessun prodotto trovato");
			return new ResponseEcommerce(HttpStatus.NOT_FOUND, "non sono presenti prodotti", null);
		}
	}
	@Override
	public ResponseEcommerce getAllProductsPaginated(int page, int size) {
	    try {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Product> productsPage = productRepository.findAll(pageable);

	        List<ProductDTO> productsDTO = productMapper.toDTOs(productsPage.getContent());

	        return new ResponseEcommerce(HttpStatus.OK, "Prodotti paginati:", productsDTO);
	    } catch (Exception e) {
		    logger.error("Errore durante la paginazione dei prodotti: {}", e.getMessage(), e);
	        return new ResponseEcommerce(HttpStatus.INTERNAL_SERVER_ERROR, "Errore interno durante la paginazione dei prodotti.", null);
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
			logger.info("Prodotto aggiornato: {}", prodotto);
			return prodotto;
		} else {
			logger.error("Errore durante l'aggiornamento del prodotto: {}", prodotto);
			return null;}
	}

	@Override
	public ResponseEcommerce createNewProduct(ProductDTO prodotto) {
		if (checkProdotto(prodotto)) {
			Product productEntity = productMapper.toEntity(prodotto);
			productRepository.save(productEntity);
			logger.info("Prodotto creato: {}", prodotto);
			return new ResponseEcommerce(HttpStatus.OK, "nuovo prodotto creato :", prodotto);
		} else {
			logger.error("Errore durante la creazione del prodotto: {}", prodotto);
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "non è possibile creare un Prodotto vuoto.", null);
		}
	}

	private boolean checkProdotto(ProductDTO prodotto) {
		if (prodotto == null) {
			return false;
		}
		if (prodotto.getCode() == null || prodotto.getCode().isBlank()) {
			return false;
		}
		if (prodotto.getName() == null || prodotto.getName().isBlank()) {
			return false;
		}
		if (prodotto.getStock() <= 0) {
			return false;
		}
		return true;
	}
}
