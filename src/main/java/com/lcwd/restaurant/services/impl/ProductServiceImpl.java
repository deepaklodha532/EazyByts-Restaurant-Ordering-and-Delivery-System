package com.lcwd.restaurant.services.impl;

import com.lcwd.restaurant.dtos.PageableResponse;
import com.lcwd.restaurant.dtos.ProductDto;
import com.lcwd.restaurant.entities.Category;
import com.lcwd.restaurant.entities.Product;
import com.lcwd.restaurant.exceptions.ResourceNotFoundException;
import com.lcwd.restaurant.helper.Helper;
import com.lcwd.restaurant.repositories.CategoryRepository;
import com.lcwd.restaurant.repositories.ProductRepository;
import com.lcwd.restaurant.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService  {

    @Autowired
    private ProductRepository repository ;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto create(ProductDto productDto) {
        productDto.setProjectId(UUID.randomUUID().toString());
        productDto.setAddedDate(new Date());
        Product product =  repository.save(mapper.map(productDto, Product.class)) ;
        return mapper.map(product, ProductDto.class) ;
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product =  repository.findById(productId).orElseThrow(()->new ResourceNotFoundException("This productId  Resource not found "));
        product.setTitle(productDto.getTitle()) ;
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setAddedDate(productDto.getAddedDate());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImage(productDto.getProductImage());
        Product product1= repository.save(product ) ;
        return mapper.map(product1, ProductDto.class) ;
    }

    @Override
    public ProductDto deleteProduct(String productId) {
        Product product =  repository.findById(productId).orElseThrow(()->new ResourceNotFoundException("This productId  Resource not found "));
        repository.delete(product);
        return null;
    }

    @Override
    public ProductDto getProduct(String productId) {
        Product product =  repository.findById(productId).orElseThrow(()->new ResourceNotFoundException("This productId  Resource not found "));
        return mapper.map(product, ProductDto.class) ;
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort  =  (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending() ;
        Pageable pageable = PageRequest.of(pageNumber, pageSize ,sort) ;
        Page<Product> all = repository.findAll(pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(all, ProductDto.class);
        return  pageableResponse;
    }

    @Override
    public List<ProductDto>  getAllLiveProduct() {
        List<Product> products  = repository.findByLiveTrue();
        List<ProductDto>productDtos =  products.stream().map(product -> mapper.map(product, ProductDto.class) ) .collect(Collectors.toList());
        return  productDtos;
    }

    @Override
    public List<ProductDto> searchProject(String title) {
        List<Product> products = repository.findByTitle(title);
        return products.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
         Category category =  categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found given id"));
        Product product = mapper.map(productDto, Product.class)  ;
        product.setProjectId(UUID.randomUUID().toString());
        //added
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product savedProduct = repository.save(product) ;
        return mapper.map(savedProduct ,ProductDto.class) ;

    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {

        //product fetch
        Product product = repository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product given id resource not found "));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("This category id not found "));
        product.setCategory(category);
        Product save = repository.save(product);
        return mapper.map(save, ProductDto.class) ;

    }


    //other methods
    // custom finder methods
    // query  method

}
