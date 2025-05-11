package com.ecommerceTest.ecommerceTest.mvc.model;

import com.ecommerceTest.ecommerceTest.mvc.entity.Order;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CustomerDTO {

    private Long id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String fiscalCode;
    private String email;


}