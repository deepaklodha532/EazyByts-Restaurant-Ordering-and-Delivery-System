package com.lcwd.restaurant.services;


import com.lcwd.restaurant.dtos.PageableResponse;
import com.lcwd.restaurant.dtos.ProductDto;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto) ;

    // update
    ProductDto update(ProductDto productDto , String productId) ;

    // delete
    ProductDto deleteProduct(String productId) ;

    // get single
    ProductDto getProduct(String productId) ;

    // get all
    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize , String sortBy , String sortDir) ;

    //get all  : live
    List<ProductDto> getAllLiveProduct();

    //search : project
    List<ProductDto>  searchProject(String title) ;

    //create product with category
    ProductDto createWithCategory(ProductDto productDto , String categoryId ) ;
    //other methods

    //update category of product
    ProductDto updateCategory(String productId ,String categoryId) ;
}
