package com.example.product_api;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;

@DataJpaTest
public class ProductIntegrationTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void testSaveAndFind() {
        Product p = new Product();
        p.setName("Test");
        p.setPrice(10.0);

        Product saved = repository.save(p);

        assertNotNull(saved.getId());

        Product found = repository.findById(saved.getId()).orElseThrow();
        assertEquals("Test", found.getName());
    }

    @Test
    void testFindByName() {
        Product p1 = new Product();
        p1.setName("Clavier");
        p1.setPrice(50);
        repository.save(p1);

        List<Product> result = repository.findByName("Clavier");
        assertEquals(1, result.size());
    }
}
