package com.lcwd.restaurant.services;


import com.lcwd.restaurant.dtos.OrderDto;
import com.lcwd.restaurant.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(OrderDto orderDto , String userId , String cartId) ;

    //remove order
    void removeOrder(String userId)  ;

    //get order of user
    List<OrderDto> getOrdersOfUser(String userId) ;

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize,String sortBy, String sortDir) ;

    //order method(logic) related to order


}
