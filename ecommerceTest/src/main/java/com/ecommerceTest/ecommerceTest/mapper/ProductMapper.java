package com.ecommerceTest.ecommerceTest.mapper;

import com.ecommerceTest.ecommerceTest.mvc.entity.Product;
import com.ecommerceTest.ecommerceTest.mvc.model.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ProductMapper {
	 ProductDTO toDTO(Product product);
	 Product toEntity(ProductDTO productDTO);
	 List<ProductDTO> toDTOs(List<Product> prodotti);
	 List<Product> toEntities(List<ProductDTO> productDTOS);
}
