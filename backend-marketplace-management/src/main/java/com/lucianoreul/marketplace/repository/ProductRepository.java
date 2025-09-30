package com.lucianoreul.marketplace.repository;

import com.lucianoreul.marketplace.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContainingIgnoreCase(String title);
    List<Product> findByStatusIgnoreCase(String status);

}
