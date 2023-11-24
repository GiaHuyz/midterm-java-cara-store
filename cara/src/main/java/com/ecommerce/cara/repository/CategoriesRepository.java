package com.ecommerce.cara.repository;

import com.ecommerce.cara.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Integer> {
}
