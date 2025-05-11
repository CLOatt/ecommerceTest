package com.ecommerceTest.ecommerceTest.mapper;

import com.ecommerceTest.ecommerceTest.mvc.entity.OrderProduct;
import com.ecommerceTest.ecommerceTest.mvc.model.OrderProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {
	OrderProductDTO toDTO(OrderProduct orderProduct);
	OrderProduct toEntity(OrderProductDTO orderProductDTO);
	List<OrderProductDTO> toDTOs(List<OrderProduct> orderProduct);
	List<OrderProduct>toEntities(List<OrderProductDTO> orderProductDTOs);
}
