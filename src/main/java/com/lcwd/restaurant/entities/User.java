package com.lcwd.restaurant.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    @Column(name = "user_name")
    private String name;
    @Column(unique = true)
    private String email ;
    @Column(name = "user_password" )
    private String password;
    private String gender ;
    private String about;
    private String imageName;

}
