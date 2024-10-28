package com.lcwd.restaurant.dtos;

import com.lcwd.restaurant.entities.CartItem;
import com.lcwd.restaurant.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CartDto {
    private String cartId ;
    private Date createdAt  ;
    private User user ;
    //mapping cart-items
    private List<CartItem> items = new ArrayList<>( ) ;

}
