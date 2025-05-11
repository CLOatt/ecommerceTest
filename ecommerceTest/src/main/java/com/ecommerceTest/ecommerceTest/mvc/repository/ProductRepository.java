package com.ecommerceTest.ecommerceTest.mvc.repository;

import com.ecommerceTest.ecommerceTest.mvc.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findByCode(String code);
//	public Prodotto findByCodice(String codice);
}