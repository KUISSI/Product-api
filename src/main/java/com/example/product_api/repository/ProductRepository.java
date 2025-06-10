package com.example.product_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product_api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
}