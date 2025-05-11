package com.ecommerceTest.ecommerceTest.mvc.service.impl;

import com.ecommerceTest.ecommerceTest.exceptions.OrderNotFoundException;
import com.ecommerceTest.ecommerceTest.mapper.CustomerMapper;
import com.ecommerceTest.ecommerceTest.mapper.OrderProductMapper;
import com.ecommerceTest.ecommerceTest.mapper.ProductMapper;
import com.ecommerceTest.ecommerceTest.mvc.entity.Order;
import com.ecommerceTest.ecommerceTest.mvc.entity.OrderProduct;
import com.ecommerceTest.ecommerceTest.mvc.entity.Customer;
import com.ecommerceTest.ecommerceTest.mvc.model.OrderDTO;
import com.ecommerceTest.ecommerceTest.mvc.model.ProductDTO;
import com.ecommerceTest.ecommerceTest.mvc.repository.CustomerRepository;
import com.ecommerceTest.ecommerceTest.mvc.repository.OrderProductRepository;
import com.ecommerceTest.ecommerceTest.mvc.repository.OrderRepository;
import com.ecommerceTest.ecommerceTest.mvc.service.OrderService;
import com.ecommerceTest.ecommerceTest.mvc.service.ProductService;
import com.ecommerceTest.ecommerceTest.utility.ResponseEcommerce;
import com.ecommerceTest.ecommerceTest.utility.orderEnums.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CustomerMapper customerMapper;

	@Autowired
	ProductMapper prodottoMapper;
	@Autowired
	ProductService productService;
	@Autowired
	OrderProductMapper orderProductMapper;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	OrderProductRepository orderProductRepository;

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


	@Override
	public ResponseEcommerce getAllOrder() {

		List<Order> ordini = orderRepository.findAll();

		List<OrderDTO> ordiniDTO= new ArrayList<>();
		OrderDTO orderDTO= null;


		if (!ordini.isEmpty()) {
			for (Order order : ordini) {
				Map<String, Integer> orderAmount = new HashMap<>();
				orderDTO = new OrderDTO();
				List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(order.getId());
				logger.info("orderProduct {} ",orderProducts);
				if (orderProducts != null && !orderProducts.isEmpty()) {
					for (OrderProduct orderProduct : orderProducts) {
						if (orderProduct.getProduct() != null) {
							orderAmount.put(orderProduct.getProduct().getCode(), orderProduct.getAmount());
						}
					}
				}
				orderDTO.setId(order.getId());
				orderDTO.setCliente(customerMapper.toDTO(order.getCustomer()));
				orderDTO.setProductAmount(orderAmount);
				orderDTO.setOrderStatus(order.getOrderStatus().name());
				ordiniDTO.add(orderDTO);

				log.info("Ordini trovati: {}", ordiniDTO);

			}

			log.info("Ordini trovati: {}", ordiniDTO);

			return new ResponseEcommerce(HttpStatus.OK, "Ordini:", ordiniDTO);

		}
		return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "ordini non presenti.", null);


	}
@Override
public ResponseEcommerce getAllOrdersPaginated(int page, int size) {
    try {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> ordersPage = orderRepository.findAll(pageable);

        List<OrderDTO> ordersDTO = new ArrayList<>();
        for (Order order : ordersPage.getContent()) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setCliente(customerMapper.toDTO(order.getCustomer()));
            orderDTO.setOrderStatus(order.getOrderStatus().name());

            // Fetch and map OrderProduct details
            Map<String, Integer> orderAmount = new HashMap<>();
            List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(order.getId());
            for (OrderProduct orderProduct : orderProducts) {
                if (orderProduct.getProduct() != null) {
                    orderAmount.put(orderProduct.getProduct().getCode(), orderProduct.getAmount());
                }
            }
            orderDTO.setProductAmount(orderAmount);
            ordersDTO.add(orderDTO);
        }

        return new ResponseEcommerce(HttpStatus.OK, "Paginated orders retrieved successfully", ordersDTO);
    } catch (Exception e) {
        logger.error("Error during order pagination: {}", e.getMessage(), e);
        return new ResponseEcommerce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error during order pagination.", null);
    }
}
	@Override
	public ResponseEcommerce createNewOrdine(OrderDTO orderDTO) {
		try {
			// Verifica che i parametri non siano nulli
			if (orderDTO == null || orderDTO.getCliente() == null || orderDTO.getProductAmount() == null) {
				return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Parametri di ordine non validi", null);
			}
			logger.info("CUSTOMER DTO {}", orderDTO.getCliente());
			Customer customer = customerRepository.findByFiscalCode(orderDTO.getCliente().getFiscalCode());
			logger.info("CUSTOMER ENTITY {}", customer);
			if (customer == null) {
				log.info("Cliente non valido {}", customer);
				return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Cliente non valido.", null);
			}

			// Imposta i prodotti nell'ordine
			orderDTO.setOrderStatus(OrderStatus.ORDINATO.name());

			Order orderEntity = new Order();
			orderEntity.setCustomer(customer);
			orderEntity.setOrderStatus(OrderStatus.ORDINATO);

			orderRepository.save(orderEntity);
			//lista ordineprodotti
			for (Map.Entry<String, Integer> entry : orderDTO.getProductAmount().entrySet()) {
				String codiceProdotto = entry.getKey();
				int amountRequest = entry.getValue();

				// Recupera il prodotto tramite codice
				ProductDTO prodotto = productService.getProductByCode(codiceProdotto);
				if (prodotto == null) {
					return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Prodotto con codice " + codiceProdotto + " non trovato.", null);
				}

				// Verifica la disponibilità del prodotto
				if (amountRequest > prodotto.getStock()) {
					return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "La quantità richiesta per il prodotto " + codiceProdotto + " supera la quantità disponibile in stock.", null);
				}

				// Aggiorna il prodotto in base alla quantità richiesta
				productService.updateProduct(prodotto, amountRequest);

				// Crea l'oggetto OrdineProdotto e aggiungilo alla lista
				OrderProduct orderProduct = new OrderProduct();
				orderProduct.setOrder(orderEntity);

				orderProduct.setProduct(prodottoMapper.toEntity(prodotto)); // Assicurati che il metodo `prodottoMapper.toEntity()` esista
				orderProduct.setAmount(amountRequest);

				orderProductRepository.save(orderProduct);

			}

//			orderDTO.setProductAmount(orderDTO.getProductAmount());
			orderDTO.setCliente(customerMapper.toDTO(customer));
			// Restituisci la risposta con l'ordine creato
			log.info("ORDINE CREATO {}", orderDTO);
			return new ResponseEcommerce(HttpStatus.OK, "Nuovo ordine effettuato con successo.", orderDTO);

		} catch (Exception e) {
			logger.error("Errore durante la creazione dell'ordine: {}", e.getMessage(), e);
			return new ResponseEcommerce(HttpStatus.INTERNAL_SERVER_ERROR, "Errore interno durante la creazione dell'ordine.", null);
		}
	}


	@Override
	public ResponseEcommerce getOrderByCustomer(String customerFiscalCode) {
		try {
			if (customerFiscalCode != null && !customerFiscalCode.isBlank()) {
				//implementazione senza l'utilizzo di una join
//			Customer customer = customerRepository.findByFiscalCode(customerFiscalCode);
//			List<Order> order = orderRepository.findByCliente(customer);
				//implementazione con l'utilizzo di una join
				List<Order> order = orderRepository.findOrdersByCustomerFiscalCode(customerFiscalCode);


				if (!order.isEmpty()) {
					return new ResponseEcommerce(HttpStatus.OK, "Ordine trovato:", order);
				} else {
					return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Non sono presenti ordini per questo cliente", null);
				}
			}
		} catch (Exception e) {
			logger.error("Errore durante la creazione dell'ordine: {}", e.getMessage(), e);
			return new ResponseEcommerce(HttpStatus.INTERNAL_SERVER_ERROR, "Errore interno durante la creazione dell'ordine.", null);
		}
		return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "codice fiscale cliente non valido", null);
	}

	@Override
	public ResponseEcommerce deleteOrderById(Long orderId) {
		try {
			if (orderId == null) {
				return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "ID ordine non valido", null);
			}
			// Verifica se l'ordine esiste
			Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Ordine inesistente"));
			if ((OrderStatus.CONSEGNATO.name().equals(order.getOrderStatus().name())|| (OrderStatus.SPEDITO.name().equals(order.getOrderStatus().name())))) {
				return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Non è possibile eliminare un ordine già spedito", null);
			}

			List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(orderId);

			if (orderProducts != null && !orderProducts.isEmpty()) {
				orderProductRepository.deleteAll(orderProducts);

				orderRepository.deleteById(orderId);
			}
			return new ResponseEcommerce(HttpStatus.OK, "Order deleted successfully", null);
		} catch (Exception e) {
			logger.error("Errore durante la creazione dell'ordine: {}", e.getMessage(), e);
			return new ResponseEcommerce(HttpStatus.INTERNAL_SERVER_ERROR, "Errore interno durante la cancellazione dell'ordine.", null);
		}
	}

	@Override
	public ResponseEcommerce updateOrderStatus(Long orderId, String orderStatus) {
		try {
			if (orderId == null || orderStatus == null || orderStatus.isBlank()) {
				logger.error("ID ordine o stato ordine non valido");
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
					logger.error("Stato ordine non valido: {}", orderStatus);
					return new ResponseEcommerce(HttpStatus.BAD_REQUEST, "Stato ordine non valido", null);

			}

			orderRepository.save(order);
			logger.info("Stato ordine aggiornato: {}", order);
			return new ResponseEcommerce(HttpStatus.OK, "Stato dell'ordine aggiornato con successo", null);
		} catch (OrderNotFoundException e) {
			logger.error("Errore: {}", e.getMessage(), e);
			return new ResponseEcommerce(HttpStatus.NOT_FOUND, e.getMessage(), null);
		} catch (Exception e) {
			logger.error("Errore durante l'aggiornamento dello stato dell'ordine: {}", e.getMessage(), e);
			return new ResponseEcommerce(HttpStatus.INTERNAL_SERVER_ERROR, "Errore interno durante l'aggiornamento dello stato dell'ordine.", null);
		}
	}

}
