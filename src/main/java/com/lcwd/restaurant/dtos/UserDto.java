package com.lcwd.restaurant.dtos;

import com.lcwd.restaurant.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    private String userId;
    @Size(min = 3, max = 50, message = "invalid name")
    private String name;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$" ,message = "invalid email")
    @Email(message = "Invalid email")
    private String email ;
    @NotBlank(message = "password is required ")
    private String password;
    @Size(min = 4, max = 6 , message = "wrong gender")
    private String gender ;
    @NotBlank(message = "write something about you  it is required ")
    private String about;
    @ImageNameValid
    private String imageName;
}
