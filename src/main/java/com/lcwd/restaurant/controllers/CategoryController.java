package com.lcwd.restaurant.controllers;


import com.lcwd.restaurant.dtos.CategoryDto;
import com.lcwd.restaurant.dtos.CustomMessage;
import com.lcwd.restaurant.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService service ;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    //create
    @PostMapping
    public ResponseEntity<CategoryDto > create( @Valid @RequestBody CategoryDto categoryDto) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(categoryDto) ;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto > getSingle(@PathVariable String categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getSingleCategory(categoryId));
    }
//
//    @GetMapping
//    public ResponseEntity<CategoryDto > getAll(@PathVariable String categoryId){
//        return ResponseEntity.status(HttpStatus.OK).body(service.getSingleCategory(categoryId));
//    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CustomMessage> deleteCategory(@PathVariable String categoryId){
        service.deleteCategory(categoryId);
        CustomMessage message = new CustomMessage();
        message.setMessage("deleted category "+categoryId);
        message.setSuccess(true);
        return  ResponseEntity.status(HttpStatus.OK).body(message) ;
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String title)
    {
        return ResponseEntity.status(HttpStatus.OK).body(service.searchCategory(title));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @Valid @RequestBody CategoryDto categoryDto){
        return new ResponseEntity(service.updateCategory(categoryId,categoryDto),HttpStatus.OK);
    }


}
