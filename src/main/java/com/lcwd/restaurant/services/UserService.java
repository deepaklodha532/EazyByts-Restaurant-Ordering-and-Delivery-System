package com.lcwd.restaurant.services;

import com.lcwd.restaurant.dtos.UserDto;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto) ;

    //update
    UserDto updateUser(String userId , UserDto userDto);

    // delete
    void deleteUser(String userid) ;

    // get all  users

    List<UserDto > getAllUser();

    // get single user by  Id
    UserDto getUserById(String userId) ;


    //other user specific features

    List<UserDto> serachByKeyword(String name)  ;


}
