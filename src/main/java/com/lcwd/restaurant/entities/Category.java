package com.lcwd.restaurant.entities;


import com.lcwd.restaurant.dtos.ProductDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    private String categoryId;
    @Column(name = "category_title", length = 60 , nullable = false)
    private String title ;
    @Column(name = "category_desc", length = 50)
    private String description ;
    private String coverImage ;
// other attributes if you user and write
    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<>() ;

}
