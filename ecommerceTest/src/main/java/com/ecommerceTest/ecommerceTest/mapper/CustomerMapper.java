package com.ecommerceTest.ecommerceTest.mapper;
import com.ecommerceTest.ecommerceTest.mvc.entity.Customer;
import com.ecommerceTest.ecommerceTest.mvc.model.CustomerDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerDTO toDTO(Customer customer);
	Customer toEntity(CustomerDTO customerDTO);
	List<CustomerDTO> toDTOs(List<Customer> clienti);
	List<Customer> toEntities(List<CustomerDTO> customerDTOS);
}
