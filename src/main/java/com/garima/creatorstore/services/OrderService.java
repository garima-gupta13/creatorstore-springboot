package com.garima.creatorstore.services;

import com.garima.creatorstore.dto.OrderItemRequest;
import com.garima.creatorstore.dto.OrderRequest;
import com.garima.creatorstore.entities.Order;
import com.garima.creatorstore.entities.OrderItem;
import com.garima.creatorstore.entities.Product;
import com.garima.creatorstore.repositories.OrderRepository;
import com.garima.creatorstore.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;


  @Transactional
  public Order createOrder(OrderRequest orderRequest)
  {
      List<OrderItem> orderItems = new ArrayList<>();
      BigDecimal totalPrice =BigDecimal.ZERO;
      Order order = new Order();
      //set properties that we are getting from order request to the Orders that we want ocreate
      order.setCustomerName(orderRequest.getCustomerName());
      order.setCustomerEmail(orderRequest.getCustomerEmail());
      order.setStatus("CONFIRMED");


      for(OrderItemRequest itemRequest : orderRequest.getItems())
      {
          //foreach item in OrderItemRequest . that user requested

          //1.check if the product requested is present in the product repository or not
          Product product = productRepository.findById(
                  itemRequest.getProductId()
          ).orElseThrow(
                  () -> new RuntimeException("Product not found with id " + itemRequest.getProductId()
                  ));

          //2. Check the product stock
          if(product.getStockQuantity() < itemRequest.getQuantity())
          {
              throw new RuntimeException("Not enough stock for "+ itemRequest.getProductId());
          }

          //3.Calculate total price
          BigDecimal priceOfItem = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
          totalPrice = totalPrice.add(priceOfItem);

          //4.Update the product table with latest stock quantity
          product.setStockQuantity(
                  product.getStockQuantity() - itemRequest.getQuantity()
          );
          productRepository.save(product);

          //5.Final calculation of total order items
          //Builder patter to make object
          OrderItem orderItem = OrderItem.builder()
                  .order(order)
                  .product(product)
                  .quantity(itemRequest.getQuantity())
                  .priceAtPurchase(product.getPrice())
                  .build();

          //6. save order  so tha twe can return to user
          orderItems.add(orderItem);
      }
      order.setTotalPrice(totalPrice);
      order.setOrderItems(orderItems);
      return orderRepository.save(order);
  }

  public List<Order> getAllOrders(){
        return orderRepository.findAll();
  }

  public Order getOrderById(Long id){
      return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with order id: "+ id));
  }
}
