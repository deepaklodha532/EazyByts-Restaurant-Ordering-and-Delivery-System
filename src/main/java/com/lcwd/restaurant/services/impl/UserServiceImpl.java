package com.lcwd.restaurant.services.impl;

import com.lcwd.restaurant.dtos.UserDto;
import com.lcwd.restaurant.entities.User;
import com.lcwd.restaurant.repositories.UserRepository;
import com.lcwd.restaurant.services.UserService;
import com.lcwd.restaurant.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository ;
    private  ModelMapper mapper ;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        User saved = userRepository.save(mapper.map(userDto , User.class))  ;
        return mapper.map(saved , UserDto.class);
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        User user =  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("This id  resource not found  " ));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        User saved =  userRepository.save(user) ;
        return mapper.map(saved, UserDto.class) ;
    }

    @Override
    public void deleteUser(String userId) {
        User user =  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("This id  resource not found  " ));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser() {
         List<User>  users =  userRepository.findAll();
         return  users.stream().map(user -> mapper.map(user,UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(String userId) {
        User user =  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("This id  resource not found  " ));
        return mapper.map(user, UserDto.class) ;

    }

    @Override
    public List<UserDto> serachByKeyword(String name) {
        return userRepository
                .findByName(name)
                .orElseThrow(()->new ResourceNotFoundException("This name resource not found " ))
                .stream().map(user -> mapper
                        .map(user,UserDto.class))
                .collect(Collectors.toList());
    }
}
