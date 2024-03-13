package com.coding.tech.orderservice.serviceImpl;

import com.coding.tech.orderservice.Repository.OrderRepository;
import com.coding.tech.orderservice.dto.InventoryResponse;
import com.coding.tech.orderservice.dto.OrderLineItemsDto;
import com.coding.tech.orderservice.dto.OrderRequest;
import com.coding.tech.orderservice.model.Order;
import com.coding.tech.orderservice.model.OrderLineItems;
import com.coding.tech.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final WebClient.Builder webClientBuilder;
    @Override
    public void placeOrder(OrderRequest orderRequest) {
           Order order= new Order();
           order.setOrderNumber(UUID.randomUUID().toString());
       List<OrderLineItems> orderlineitems = orderRequest.getOrderLineItemsDto().stream().map(this::mapToDto).toList();
       order.setOrderLineItems(orderlineitems);

    List<String> skucodes=   order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();
       //call the inventory service and place order if product is in stock
        //bodytomono is used to read data from api and in this method we have to mentioned what datatype is return
        //when we add block method it will make synchronus communication between api
   InventoryResponse [] inventoryResponse=     webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode",skucodes).build())
                        .retrieve()
                                .bodyToMono(InventoryResponse [].class)
           .block();
    boolean allProductsInstock=    Arrays.stream(inventoryResponse).allMatch(InventoryResponse::isStock);
        if(allProductsInstock) {
         orderRepository.save(order);
     }else{
         throw new IllegalArgumentException("Product is not in stock");
     }
    }

  private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
    OrderLineItems orderLineItems  = new OrderLineItems();
    orderLineItems.setPrice(orderLineItemsDto.getPrice());
    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
return orderLineItems;
  }
}
