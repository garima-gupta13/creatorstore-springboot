package com.garima.creatorstore.controllers;


import com.garima.creatorstore.dto.OrderRequest;
import com.garima.creatorstore.entities.Order;
import com.garima.creatorstore.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@Valid @RequestBody OrderRequest orderRequest)
    {
        return orderService.createOrder(orderRequest);
    }

    //Get all orders
    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id){

        return orderService.getOrderById(id);
    }
}
