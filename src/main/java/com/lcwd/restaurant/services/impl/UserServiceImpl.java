package com.lcwd.restaurant.services.impl;

import com.lcwd.restaurant.dtos.PageableResponse;
import com.lcwd.restaurant.dtos.UserDto;
import com.lcwd.restaurant.entities.User;
import com.lcwd.restaurant.helper.Helper;
import com.lcwd.restaurant.repositories.UserRepository;
import com.lcwd.restaurant.services.UserService;
import com.lcwd.restaurant.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class) ;

    private UserRepository userRepository ;
    private  ModelMapper mapper ;


    @Value("${user.profile.image.path}")
    private String imagePath ;



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

        //delete user profile image
        //images/user/adc.jpg

        String fullPath  = imagePath +  user.getImageName() ;
        try{
            Path path  = Paths.get(fullPath) ;
            Files.delete(path) ;
        }
        catch (NoSuchFileException ex) {
            logger.info(ex.getMessage());
            ex.printStackTrace();
        }
        catch (IOException e) {
           e.printStackTrace();
        }
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy  , String sortDir) {
         Sort sort = (sortDir.equalsIgnoreCase("asc"))?   Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
         Pageable pageable =  PageRequest.of(pageNumber,pageSize ,sort) ;
         Page<User> page =  userRepository.findAll(pageable);


        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);
        return pageableResponse ;
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
