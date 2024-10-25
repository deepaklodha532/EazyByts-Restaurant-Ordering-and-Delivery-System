package com.lcwd.restaurant.repositories;

import com.lcwd.restaurant.dtos.CategoryDto;
import com.lcwd.restaurant.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
  Optional<Category> findByTitle(String title) ;
}
