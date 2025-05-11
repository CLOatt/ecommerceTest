package com.ecommerceTest.ecommerceTest.mvc.entity;

import com.ecommerceTest.ecommerceTest.utility.orderEnums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "ORDER_ECOMMERCE")
public class Order {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Column(name = "ORDER_ID")
      private Long id;

      @ManyToOne
      @JoinColumn(name = "CUSTOMER_ID")
      private Customer customer;

      @Column(name = "STATO")
      @Enumerated(EnumType.STRING)
      private OrderStatus orderStatus;




}