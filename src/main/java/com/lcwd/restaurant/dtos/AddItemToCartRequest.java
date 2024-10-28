package com.lcwd.restaurant.dtos;

import lombok.Data;

@Data
public class AddItemToCartRequest {
    private String productId;
    private int quantity ;

}
