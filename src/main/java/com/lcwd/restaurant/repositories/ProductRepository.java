package com.lcwd.restaurant.repositories;

import com.lcwd.restaurant.dtos.ProductDto;
import com.lcwd.restaurant.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>  {
    List<Product> findByTitle(String title)  ;
    List<Product> findByLiveTrue() ;

}
