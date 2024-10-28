package com.lcwd.restaurant.dtos;

import com.lcwd.restaurant.entities.Category;
import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {
    private String projectId;
    private String title ;
    private String description ;
    private int price ;
    private int discountedPrice ;
    private int quantity ;
    private Date addedDate ;
    private boolean live;
    private boolean stock  ;
    private String productImage;
    private CategoryDto category  ;

}
