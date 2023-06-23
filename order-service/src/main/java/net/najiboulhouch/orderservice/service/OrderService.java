package net.najiboulhouch.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.najiboulhouch.orderservice.dto.InventoryResponse;
import net.najiboulhouch.orderservice.dto.OrderLineItemsDto;
import net.najiboulhouch.orderservice.dto.OrderRequest;
import net.najiboulhouch.orderservice.event.OrderPlacedEvent;
import net.najiboulhouch.orderservice.model.Order;
import net.najiboulhouch.orderservice.model.OrderLineItems;
import net.najiboulhouch.orderservice.repository.OrderRepository;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder ;

    private final Tracer tracer;

    private final KafkaTemplate<String , OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        
        Set<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());

        orderLineItems.forEach(order::add);

        order.setOrderLineItems(orderLineItems);


        List<String> skuCodes = order.getOrderLineItems()
                .stream()
                .map(OrderLineItems::getSkuCode).toList();

        log.info("Calling inventory service");

       Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())) {

            //Call Inventory Service, and place order if product is in stock
            InventoryResponse[] inventoryResponses =  webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory/",
                            uriBuilder -> uriBuilder.queryParam("skuCode" , skuCodes)
                                    .build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();


            boolean allProductsInStock =  Arrays.stream(inventoryResponses)
                    .allMatch(InventoryResponse::isInStock);

            if(allProductsInStock) {
                orderRepository.save(order);
                kafkaTemplate.send("notificationTopic" , new OrderPlacedEvent(order.getOrderNumber()));
                return "Order Placed Successfully";
            }
            else {
                throw new IllegalArgumentException("Product is not in Stock, Please try again later.");
            }
        }
        finally {
            inventoryServiceLookup.end();
        }






    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
