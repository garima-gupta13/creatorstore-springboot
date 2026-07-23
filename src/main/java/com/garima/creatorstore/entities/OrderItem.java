package com.garima.creatorstore.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name ="order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message= "quantity cannot be null")
    @Column(nullable = false)
    private Integer quantity;

    @Column(name="price_at_purchase", nullable = false)
    private BigDecimal priceAtPurchase;

    //TODO: relations == i.e 1 to many
    // we have tables and we have to connect them , means make relations

    @ManyToOne
    @JoinColumn(name= "order_id", nullable= false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
