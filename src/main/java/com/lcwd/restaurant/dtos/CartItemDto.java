package com.lcwd.restaurant.dtos;


import lombok.Data;

@Data
public class CartItemDto {
    private int cartItemId ;
    private ProductDto product ;
    private int quantity ;
    private int totalPrice ;
    //mapping price
}
