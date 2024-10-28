package com.lcwd.restaurant.services.impl;

import com.lcwd.restaurant.dtos.OrderDto;
import com.lcwd.restaurant.dtos.PageableResponse;
import com.lcwd.restaurant.entities.*;
import com.lcwd.restaurant.exceptions.BadApiRequestException;
import com.lcwd.restaurant.exceptions.ResourceNotFoundException;
import com.lcwd.restaurant.helper.Helper;
import com.lcwd.restaurant.repositories.CartRepository;
import com.lcwd.restaurant.repositories.OrderRepository;
import com.lcwd.restaurant.repositories.UserRepository;
import com.lcwd.restaurant.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private OrderRepository orderRepository ;


    @Autowired
    private ModelMapper mapper ;

    @Autowired
    private CartRepository cartRepository ;

    @Override
    public OrderDto createOrder(OrderDto orderDto, String userId ,String cartId) {

        //fetch  user
        orderDto.setOrderId(UUID.randomUUID().toString());
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(" this user not found given  id "));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("this cart item is not available "));

        List<CartItem> cartItems =cart.getItems() ;
        if(cartItems.size() <=0){
            throw new BadApiRequestException("invalid number of items in cart  !!") ;
        }

        //other checks

        Order order = new Order()   ;
        order.setBillingName(orderDto.getBillingName()) ;
        order.setBillingPhone(orderDto.getBillingPhone());
        order.setBillingAddress(orderDto.getBillingAddress());
        order.setOrderedDate(new Date());
        order.setDeliverDate(orderDto.getDeliverDate());
        order.setPaymentStatus(orderDto.getPaymentStatus());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setOrderId(UUID.randomUUID().toString());
        //orderitem  and amount i didn't set
        //order items ,  amount

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0) ;

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
//            CartItem->OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice());
            orderItem.setOrder(order);
            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());
        //
        cart.getItems().clear();
        cartRepository.save(cart) ;
        Order save = orderRepository.save(order);
        return mapper.map(save, OrderDto.class) ;
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("this order is not found"));
        orderRepository.delete(order) ;

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("This resource not found"));
        List<Order> orders = orderRepository.findByUser(user) ;
        orders.stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList()) ;
      return null ;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending() ;
        Pageable pageable =  PageRequest.of(pageNumber, pageSize ,sort) ;
        Page<Order> page = orderRepository.findAll(pageable);
        PageableResponse<OrderDto> response = Helper.getPageableResponse(page , OrderDto.class) ;
        return response ;
    }
}

