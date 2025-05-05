package com.ecommerceTest.ecommerceTest.mvc.service.impl;

import com.ecommerceTest.ecommerceTest.exceptions.OrderNotFoundException;
import com.ecommerceTest.ecommerceTest.mapper.CustomerMapper;
import com.ecommerceTest.ecommerceTest.mapper.OrderMapper;
import com.ecommerceTest.ecommerceTest.mapper.ProductMapper;
import com.ecommerceTest.ecommerceTest.mvc.entity.Order;
import com.ecommerceTest.ecommerceTest.mvc.entity.OrderProduct;
import com.ecommerceTest.ecommerceTest.mvc.entity.Customer;
import com.ecommerceTest.ecommerceTest.mvc.model.CustomerDTO;
import com.ecommerceTest.ecommerceTest.mvc.model.OrderDTO;
import com.ecommerceTest.ecommerceTest.mvc.model.ProductDTO;
import com.ecommerceTest.ecommerceTest.mvc.repository.CustomerRepository;
import com.ecommerceTest.ecommerceTest.mvc.repository.OrderRepository;
import com.ecommerceTest.ecommerceTest.mvc.service.OrderService;
import com.ecommerceTest.ecommerceTest.mvc.service.ProductService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import com.ecommerceTest.ecommerceTest.utility.orderEnums.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	ProductMapper prodottoMapper;
	@Autowired
	ProductService productService;
	@Autowired
	CustomerRepository customerRepository;


	@Override
	public ResponseEcommerce getAllOrder() {

		List<Order> ordini = orderRepository.findAll();

		if (!ordini.isEmpty()) {
			return new ResponseEcommerce(HttpStatus.OK, "Ordini:", ordini);


		}
		return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "ordini non presenti.", null);


	}

	@Override
	public ResponseEcommerce createNewOrdine(OrderDTO orderDTO, CustomerDTO customerDTO, Map<String, Integer> prodottiQuantita) {
		if (orderDTO != null && customerDTO != null && prodottiQuantita != null && !prodottiQuantita.isEmpty()) {
			// Initialize the order
			orderDTO.setCliente(customerDTO);

			List<OrderProduct> ordineProdotti = new ArrayList<>();
			for (Map.Entry<String, Integer> entry : prodottiQuantita.entrySet()) {
				String codiceProdotto = entry.getKey();
				int amountRequest = entry.getValue();

				ProductDTO prodotto = productService.getProductByCode(codiceProdotto);
				if (prodotto == null) {
					return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Prodotto con codice " + codiceProdotto + " non trovato.", null);
				}

				if (amountRequest > prodotto.getStock()) {
					return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "La quantità richiesta per il prodotto " + codiceProdotto + " supera la quantità disponibile in stock.", null);
				}

				productService.updateProduct(prodotto, amountRequest);

				// Create OrdineProdotto
				OrderProduct orderProduct = new OrderProduct();
				orderProduct.setOrder(orderMapper.toEntity(orderDTO));
				orderProduct.setProduct(prodottoMapper.toEntity(prodotto));
				orderProduct.setAmount(amountRequest);
				ordineProdotti.add(orderProduct);
			}

			// Set products in the order
			orderDTO.setProdotti(ordineProdotti);
			orderDTO.setOrderStatus(OrderStatus.ORDINATO.name());
			orderRepository.save(orderMapper.toEntity(orderDTO));

			return new ResponseEcommerce(HttpStatus.OK, "Nuovo ordine effettuato con successo.", orderDTO);
		} else {
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Non è stato possibile completare l'ordine.", null);
		}
	}
	@Override
	public ResponseEcommerce getOrderByCustomer(String customerFiscalCode) {

		if(customerFiscalCode != null && !customerFiscalCode.isBlank()){
			//implementazione senza l'utilizzo di una join
//			Customer customer = customerRepository.findByFiscalCode(customerFiscalCode);
//			List<Order> order = orderRepository.findByCliente(customer);
			//implementazione con l'utilizzo di una join
			List<Order> order = orderRepository.findOrdersByCustomerFiscalCode(customerFiscalCode);


			if(order !=null){
				return new ResponseEcommerce(HttpStatus.OK, "Ordine trovato:", order);
			}else {
				return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Non sono presenti ordini per questo cliente", null);
			}
		}

		return null;
	}
	@Override
	public ResponseEcommerce deleteOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Ordine inesistente"));
	if (OrderStatus.CONSEGNATO.name().equals(order.getOrderStatus().name())) {
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Non è possibile eliminare un ordine già spedito", null);
		}
		orderRepository.deleteById(orderId);
		return new ResponseEcommerce(HttpStatus.OK, "Order deleted successfully",null);
	}

	@Override
	public ResponseEcommerce updateOrderStatus(Long orderId, String orderStatus) {
		if(orderId == null || orderStatus == null || orderStatus.isBlank()) {
			return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "ID ordine o stato ordine non valido", null);
		}
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Ordine inesistente"));
		switch (orderStatus) {
			case "CONSEGNATO":
				order.setOrderStatus(OrderStatus.CONSEGNATO);
				break;
			case "SPEDITO":
				order.setOrderStatus(OrderStatus.SPEDITO);
				break;
			case "ORDINATO":
				order.setOrderStatus(OrderStatus.ORDINATO);
				break;
			default:
				return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Stato ordine non valido", null);

		}

		orderRepository.save(order);
		return new ResponseEcommerce(HttpStatus.OK, "Stato dell'ordine aggiornato con successo", null);
	}

}
