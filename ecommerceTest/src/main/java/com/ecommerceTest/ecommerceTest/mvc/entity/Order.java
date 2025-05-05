package com.ecommerceTest.ecommerceTest.mvc.entity;

import com.ecommerceTest.ecommerceTest.utility.orderEnums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "ORDINE")
public class Order {


      @Id
      @Column(name = "ID_ORDINE")
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @ManyToOne
      @JoinColumn(name = "cliente_id", nullable = false)
      private Customer customer;

      @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<OrderProduct> prodotti ;
      @Column(name="STATO")
      @Enumerated(EnumType.STRING)
      private OrderStatus orderStatus;


}