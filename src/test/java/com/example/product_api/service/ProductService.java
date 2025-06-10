package com.example.product_api.service;

import org.springframework.stereotype.Service;

import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product duplicate(Long id) {
        Product original = repository.findById(id).orElseThrow();
        Product copy = new Product();
        copy.setName(original.getName());
        copy.setPrice(original.getPrice());
        return repository.save(copy);
    }
}
