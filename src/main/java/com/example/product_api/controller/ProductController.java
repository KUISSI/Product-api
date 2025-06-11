package com.example.product_api.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping
    public Product create(@Valid @RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        Product existing = repository.findById(id).orElseThrow();
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/{id}/duplicate")
    public Product duplicate(@PathVariable Long id) {
        Product original = repository.findById(id).orElseThrow();

        Product clone = new Product();
        clone.setName(original.getName());
        clone.setPrice(original.getPrice());

        return repository.save(clone);
    }

    @PostMapping("/bundle")
    public Product createBundle(@RequestBody List<Long> sourceIds) {
        if (sourceIds == null || sourceIds.isEmpty()) {
            throw new IllegalArgumentException("La liste des produits source est vide.");
        }

        List<Product> sources = repository.findAllById(sourceIds);

        for (Product source : sources) {
            if (hasCycle(source, sourceIds)) {
                throw new IllegalArgumentException("Boucle détectée !");
            }
        }

        Product bundle = new Product();
        bundle.setName("Bundle: " +
                sources.stream().map(Product::getName).reduce((a, b) -> a + " + " + b).orElse(""));
        bundle.setPrice(sources.stream().mapToDouble(Product::getPrice).sum());
        bundle.setSources(sources);

        return repository.save(bundle);
    }

    private boolean hasCycle(Product product, List<Long> newSourceIds) {
        if (product.getSources() == null) return false;
        for (Product source : product.getSources()) {
            if (newSourceIds.contains(source.getId()) || hasCycle(source, newSourceIds)) {
                return true;
            }
        }
        return false;
    }
}
