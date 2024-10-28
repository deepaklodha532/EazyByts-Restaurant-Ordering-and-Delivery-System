package com.lcwd.restaurant.services;

import com.lcwd.restaurant.dtos.CategoryDto;
import com.lcwd.restaurant.dtos.UserDto;
import com.lcwd.restaurant.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    //create category
    CategoryDto createCategory(CategoryDto categoryDto );

    // delete
    void deleteCategory(String categoryId) ;

    // update
    CategoryDto updateCategory(String categoryId,  CategoryDto categoryDto) ;

    // get all
    PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy  , String sortDir);

    // get single category
    CategoryDto getSingleCategory(String categoryId) ;

    //search
    List<CategoryDto> searchCategory(String name) ;
}
