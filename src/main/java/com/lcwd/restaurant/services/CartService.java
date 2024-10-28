package com.lcwd.restaurant.services;

import com.lcwd.restaurant.dtos.AddItemToCartRequest;
import com.lcwd.restaurant.dtos.CartDto;

public interface CartService {

    //add items to cart
    //case1:  cart for user is not available : we will create the cart and
    //case2:  cart available add the items to cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request) ;
    //remove item from cart
    void removeItemFromCart(String userId, int cartItem) ;

    // remove all item  from cart
    void clearCart(String userId  ) ;

    public CartDto  getCartByUser(String userId) ;

}
