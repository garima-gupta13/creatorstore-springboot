package com.garima.creatorstore.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message= "Customer name is required")
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name= "customer_email",nullable=false)
    private String customerEmail;

    @Column(nullable = false)
    private String status;

    @Column(name="total_price", nullable = false)
    private BigDecimal totalPrice;


    @OneToMany(mappedBy = "order") // mapped by order field - created in OrderItem class
    private List<OrderItem> orderItems;

    @Column(name= "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
