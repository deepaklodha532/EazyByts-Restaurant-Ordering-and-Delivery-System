package com.lcwd.restaurant.dtos;

import com.lcwd.restaurant.entities.OrderItem;
import jakarta.persistence.Column;
import lombok.Data;
import org.modelmapper.internal.bytebuddy.matcher.FilterableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private String orderId;
    private String orderStatus ="PENDING";
    private String paymentStatus="NOTPAID";
    private int totalAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate=new Date();
    private Date deliverDate;
    private int orderAmount ;

    private List<OrderItemDto> orderItems= new ArrayList<>() ;
}
