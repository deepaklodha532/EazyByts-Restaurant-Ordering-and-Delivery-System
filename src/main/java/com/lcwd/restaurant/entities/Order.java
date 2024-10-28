package com.lcwd.restaurant.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    private String orderId;

    private String orderStatus;
    //
    private String paymentStatus;

    private int totalAmount;

    @Column(length = 1000)
    private String billingAddress;


    private String billingPhone;

    private String billingName;

    private Date orderedDate;

    private Date deliverDate;

    private int orderAmount ;

    //user
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();



}