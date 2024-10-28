package com.lcwd.restaurant.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    private int quantity ;

    private int totalPrice;

    @OneToOne
    private Product product  ;

    @ManyToOne
    private Order order;

}
