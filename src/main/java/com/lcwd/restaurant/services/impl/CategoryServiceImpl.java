package com.lcwd.restaurant.services.impl;

import com.lcwd.restaurant.dtos.CategoryDto;
import com.lcwd.restaurant.dtos.PageableResponse;
import com.lcwd.restaurant.dtos.UserDto;
import com.lcwd.restaurant.entities.Category;
import com.lcwd.restaurant.exceptions.ResourceNotFoundException;
import com.lcwd.restaurant.repositories.CategoryRepository;
import com.lcwd.restaurant.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository repository ;
    private ModelMapper mapper ;

    public CategoryServiceImpl(CategoryRepository repository , ModelMapper mapper) {
        this.mapper = mapper ;
        this.repository = repository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        categoryDto.setCategoryId(UUID.randomUUID().toString()) ;
        Category saved =  repository.save(mapper.map(categoryDto , Category.class));
        return mapper.map(saved, CategoryDto.class) ;
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category =  repository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("This id category not found "));
        repository.delete(category);
    }

    @Override
    public CategoryDto updateCategory(String categoryId, CategoryDto categoryDto) {
        Category category =  repository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("This id category not found "));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        Category saved =  repository.save(category);
        return mapper.map(saved, CategoryDto.class) ;
    }

    @Override
    public PageableResponse<UserDto> getAllCategory() {
        return null;
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        Category category =  repository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("This id category not found "));
        return mapper.map(category, CategoryDto.class) ;
    }

    @Override
    public List<CategoryDto> searchCategory(String title) {
        Category category =  repository.findByTitle(title).orElseThrow(()->new ResourceNotFoundException("This id category not found "));
        return List.of();
    }
}
