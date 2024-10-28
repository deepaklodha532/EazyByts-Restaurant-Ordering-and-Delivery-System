package com.lcwd.restaurant.dtos;

import com.lcwd.restaurant.entities.Order;
import com.lcwd.restaurant.entities.Product;
import lombok.Data;

@Data
public class OrderItemDto {

    private int orderItemId;
    private int quantity ;
    private int totalPrice;
    private ProductDto product  ;
}
