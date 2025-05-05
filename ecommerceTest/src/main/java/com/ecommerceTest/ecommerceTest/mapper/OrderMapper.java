package com.ecommerceTest.ecommerceTest.mapper;

import com.ecommerceTest.ecommerceTest.mvc.entity.Order;
import com.ecommerceTest.ecommerceTest.mvc.model.OrderDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
	 OrderDTO toDTO(Order order);
	 Order toEntity(OrderDTO orderDTO);
	 List<OrderDTO> toDTOs(List<Order> ordini);
	 List<Order> toEntities(List<OrderDTO> orderDTOS);
}
