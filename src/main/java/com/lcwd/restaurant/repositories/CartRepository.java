package com.lcwd.restaurant.repositories;

import com.lcwd.restaurant.entities.Cart;
import com.lcwd.restaurant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);

}
