package com.lcwd.restaurant.controllers;

import com.lcwd.restaurant.dtos.AddItemToCartRequest;
import com.lcwd.restaurant.dtos.CartDto;
import com.lcwd.restaurant.dtos.CustomMessage;
import com.lcwd.restaurant.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService ;

    // add items to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId  ,  @RequestBody AddItemToCartRequest request){
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto , HttpStatus.CREATED) ;
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CustomMessage> removeItemFromCart(
            @PathVariable String userId,
            @PathVariable int itemId
    ){
        cartService.removeItemFromCart(userId , itemId);
        CustomMessage message =new CustomMessage();
        message.setMessage("remove successfully ");
        message.setSuccess(true) ;

        return new ResponseEntity<>(message, HttpStatus.OK) ;
    }



    @DeleteMapping("/{userId}")
    public ResponseEntity<CustomMessage> clearCart(
            @PathVariable String userId
    ){
        cartService.clearCart(userId);
        CustomMessage message =new CustomMessage();
        message.setMessage("Not cart is blank ");
        message.setSuccess(true) ;
        return new ResponseEntity<>(message, HttpStatus.OK) ;
    }
}
