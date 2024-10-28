package com.lcwd.restaurant.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    private String cartId ;
    private Date createdAt  ;
    @OneToOne
    private User user ;
    //mapping cart-items
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL , fetch = FetchType.EAGER )
    private List<CartItem> items = new ArrayList<>( ) ;

    public static class OrderItem {

    }
}
