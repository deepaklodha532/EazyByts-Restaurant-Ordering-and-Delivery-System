package com.lcwd.restaurant.dtos;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private String orderId;
    private String orderStatus ="PENDING";
    private String paymentStatus="NOTPAID";
    private int totalAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
}
