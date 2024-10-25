package com.lcwd.restaurant.repositories;

import com.lcwd.restaurant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User ,String> {

    Optional<User> findByEmail(String email) ;

    Optional<User> findByPassword(String password) ;

    Optional<List<User>> findByName(String name);
}

