package com.lcwd.restaurant.controllers;

import com.lcwd.restaurant.dtos.CustomMessage;
import com.lcwd.restaurant.dtos.UserDto;
import com.lcwd.restaurant.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private   UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto ){
        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto) );
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser() , HttpStatus.OK) ;
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
}
