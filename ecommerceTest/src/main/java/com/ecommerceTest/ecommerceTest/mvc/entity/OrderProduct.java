package com.ecommerceTest.ecommerceTest.mvc.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ordine_prodotto")
public class OrderProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;

	@Column(name="QUANTITA" ,nullable = false)
	private int amount;

}