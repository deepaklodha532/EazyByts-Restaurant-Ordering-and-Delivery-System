package com.lcwd.restaurant.repositories;

import com.lcwd.restaurant.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem ,Integer> {

}
