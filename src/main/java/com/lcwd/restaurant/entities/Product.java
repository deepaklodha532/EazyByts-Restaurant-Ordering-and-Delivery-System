package com.lcwd.restaurant.entities;

import com.lcwd.restaurant.dtos.ProductDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name ="projects")
public class Product {

    @Id
    private String projectId;
    private String title ;
    @Column(length = 1000)
    private String description ;
    private int price ;
    private int discountedPrice ;
    private int quantity ;
    private Date addedDate ;
    private boolean live;
    private boolean stock  ;

    private String productImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
    private Category category  ;
}
