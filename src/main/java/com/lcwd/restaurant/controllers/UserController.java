package com.lcwd.restaurant.controllers;

import com.lcwd.restaurant.dtos.CustomMessage;
import com.lcwd.restaurant.dtos.ImageResponse;
import com.lcwd.restaurant.dtos.PageableResponse;
import com.lcwd.restaurant.dtos.UserDto;
import com.lcwd.restaurant.services.FileService;
import com.lcwd.restaurant.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class) ;


    @Value("${user.profile.image.path}")
    String imagePath  ;

    @Autowired
    private FileService fileService ;


    private   UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto ){
        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto) );
    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "0" , required = false) int pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = "10" ,required = false) int pageSize,
            @RequestParam(value="sortDir", defaultValue = "asc" , required = false) String sortDir,
            @RequestParam(value = "sortBy", defaultValue = "name",required = false) String sortBy
    ){
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize, sortBy, sortDir) , HttpStatus.OK) ;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CustomMessage> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId) ;
        CustomMessage message = new CustomMessage() ;
        message.setMessage("deleted successfully");
        message.setSuccess(true) ;
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId  , @Valid  @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId , userDto) );
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDto>> searchByKeyword(@PathVariable String name){
        return new ResponseEntity<>(userService.serachByKeyword(name), HttpStatus.OK );
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage" )MultipartFile image ,
            @PathVariable String userId
    ) throws IOException {
        String imageName = fileService.uploadImage(image, imagePath);

        UserDto userDto = userService.getUserById(userId) ;
        userDto.setImageName(imageName);
        UserDto userDto1 =  userService.updateUser(userId ,userDto)  ;

        ImageResponse imageReponse = ImageResponse.builder()
                .imageName(imageName)
                .success(true)
                .message("upload the image ")
                .build();

        return new ResponseEntity<>(imageReponse, HttpStatus.CREATED) ;
    }

    //server user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId , HttpServletResponse response) throws IOException {
        //
        UserDto user  =  userService.getUserById(userId) ;

        logger.info("user Image name :{} ", user.getImageName()) ;
        InputStream resource = fileService.getResource(imagePath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }

}
