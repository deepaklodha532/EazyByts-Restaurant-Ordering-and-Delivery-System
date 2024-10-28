package com.lcwd.restaurant.controllers;

import com.lcwd.restaurant.dtos.*;
import com.lcwd.restaurant.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService ;

    //create
//    public ResponseEntity<OrderDto > createOrder(@RequestBody CreateOrderRequest request) {
//        OrderDto orderDto = orderService.createOrder(request) ;
//    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<CustomMessage > removeOrder(@PathVariable String orderId) {

        orderService.removeOrder(orderId) ;
        CustomMessage message = new CustomMessage() ;
        message.setMessage("successfully deleted ");
        message.setSuccess(true) ;
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }

    //ger orders of the user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> orderOfUser = orderService.getOrdersOfUser(userId) ;
        return new ResponseEntity<>(orderOfUser , HttpStatus.OK) ;
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber" , defaultValue = "0" , required = false) int pageNumber ,
            @RequestParam(value = "pageSize" , defaultValue = "10" , required = false) int pageSize ,
            @RequestParam(value = "sortBy",defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir" , defaultValue = "asc" , required = false) String sortDir
    ){
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(orders, HttpStatus.OK) ;
    }
}
