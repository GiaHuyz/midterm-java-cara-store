package com.ecommerce.cara.repository;

import com.ecommerce.cara.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUsersUsername(String username);
}
