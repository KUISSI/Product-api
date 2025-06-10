package com.example.product_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;

@RestController
@RequestMapping("/products")
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
    public Product create(@RequestBody Product product) {
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
    List<Product> sources = repository.findAllById(sourceIds);

    // Vérification récursive simple (aucun des sources ne doit contenir le bundle lui-même)
    for (Product source : sources) {
        if (hasCycle(source, sourceIds)) {
            throw new IllegalArgumentException("Boucle détectée !");
        }
    }

    Product bundle = new Product();
    bundle.setName("Bundle: " + 
        sources.stream().map(Product::getName).reduce((a, b) -> a + " + " + b).orElse(""));

    double totalPrice = sources.stream().mapToDouble(Product::getPrice).sum();
    bundle.setPrice(totalPrice);
    bundle.setSources(sources);

    return repository.save(bundle);
}

private boolean hasCycle(Product product, List<Long> newSourceIds) {
    for (Product source : product.getSources()) {
        if (newSourceIds.contains(source.getId()) || hasCycle(source, newSourceIds)) {
            return true;
        }
    }
    return false;
}


}
