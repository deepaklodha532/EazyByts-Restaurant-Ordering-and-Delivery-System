package com.lcwd.restaurant.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId ;
    @OneToOne
    private Product product ;
    private int quantity ;
    private int totalPrice ;
    //mapping price
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "cart_id")
    private Cart cart  ;




}
