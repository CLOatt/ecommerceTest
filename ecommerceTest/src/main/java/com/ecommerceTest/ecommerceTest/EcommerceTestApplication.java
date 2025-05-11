package com.ecommerceTest.ecommerceTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.ecommerceTest.ecommerceTest.mvc.entity")
public class EcommerceTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceTestApplication.class, args);
	}

}
