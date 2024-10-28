package com.lcwd.restaurant.controllers;


import com.lcwd.restaurant.dtos.CategoryDto;
import com.lcwd.restaurant.dtos.CustomMessage;
import com.lcwd.restaurant.dtos.PageableResponse;
import com.lcwd.restaurant.dtos.ProductDto;
import com.lcwd.restaurant.services.CategoryService;
import com.lcwd.restaurant.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ProductService productService ;

    private CategoryService service ;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    //create
    @PostMapping
    public ResponseEntity<CategoryDto > create( @Valid @RequestBody CategoryDto categoryDto) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(categoryDto)) ;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto > getSingle(@PathVariable String categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getSingleCategory(categoryId));
    }
//
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0" , required = false) int pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = "10" ,required = false) int pageSize,
            @RequestParam(value="sortDir", defaultValue = "asc" , required = false) String sortDir,
            @RequestParam(value = "sortBy", defaultValue = "name",required = false) String sortBy
    ){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCategory(pageNumber,pageSize,sortBy,sortDir));
    }

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

    //create product with category

    @PostMapping("/{categoryId}/product")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto)
    {
        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId) ;
        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED) ;
    }

    //update category of product
    @PutMapping("/{categoryId}/products/{productId}")
     public ResponseEntity<ProductDto> updateCategoryOfProduct(
             @PathVariable String productId, @PathVariable String categoryId
    ){
        ProductDto productDto = productService.updateCategory(productId,categoryId) ;
        return new ResponseEntity<>(productDto, HttpStatus.OK) ;
     }


}
