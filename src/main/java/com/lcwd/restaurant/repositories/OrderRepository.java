package com.lcwd.restaurant.repositories;

import com.lcwd.restaurant.entities.Order;
import com.lcwd.restaurant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order ,String > {
    List<Order> findByUser(User user) ;
}
