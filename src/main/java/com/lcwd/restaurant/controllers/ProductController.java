package com.lcwd.restaurant.controllers;

import com.lcwd.restaurant.dtos.*;
import com.lcwd.restaurant.services.FileService;
import com.lcwd.restaurant.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Struct;
import java.util.List;

@RestController
@RequestMapping("/products")
public class  ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class) ;
    //
    @Autowired
    ProductService productService;

    @Autowired
    FileService fileService ;

    @Value("${product.image.path}")
    String imagePath ;

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0" , required = false) int pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = "10" ,required = false) int pageSize,
            @RequestParam(value="sortDir", defaultValue = "asc" , required = false) String sortDir,
            @RequestParam(value = "sortBy", defaultValue = "name",required = false) String sortBy
    ) {
        PageableResponse<ProductDto> allProducts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProducts, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.create(productDto),HttpStatus.CREATED) ;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingle(@PathVariable  String productId) {
        return new ResponseEntity<>(productService.getProduct(productId), HttpStatus.OK) ;
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId ,@RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.update(productDto,productId),HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<List<ProductDto>> getLIveProduct(){
        return new ResponseEntity<>(productService.getAllLiveProduct(),HttpStatus.OK );
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<CustomMessage> deleteProduct(@PathVariable String productId)  {
        productService.deleteProduct(productId) ;
        CustomMessage message = new CustomMessage() ;
        message.setMessage("deleted successfully");
        message.setSuccess(true) ;
        return new ResponseEntity<>(message, HttpStatus.OK) ;
    }

    //upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage" )MultipartFile image
    ) throws IOException {
         String fileName = fileService.uploadImage(image, imagePath)  ;
        ProductDto productDto = productService.getProduct(productId);
        productDto.setProductImage(fileName);
        ProductDto updateProduct = productService.update(productDto, productId ) ;
        ImageResponse response =    ImageResponse.builder().imageName(updateProduct.getProductImage()).message("product image is successfully ").build() ;
        return  new ResponseEntity<>(response ,HttpStatus.CREATED) ;
    }

    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId , HttpServletResponse response) throws IOException {
        //
        ProductDto productDto   =  productService.getProduct(productId) ;

        logger.info("user Image name :{} ", productDto.getProductImage()) ;
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }





}
