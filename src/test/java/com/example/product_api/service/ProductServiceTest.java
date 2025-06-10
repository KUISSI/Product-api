package com.example.product_api.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;

public class ProductServiceTest {

    @Test
    void testDuplicate() {
        ProductRepository repository = mock(ProductRepository.class);
        ProductService service = new ProductService(repository);

        Product original = new Product();
        original.setId(1L);
        original.setName("Stylo");
        original.setPrice(2.5);

        when(repository.findById(1L)).thenReturn(Optional.of(original));
        when(repository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Product duplicated = service.duplicate(1L);

        assertNull(duplicated.getId()); // pas encore persisté
        assertEquals("Stylo", duplicated.getName());
        assertEquals(2.5, duplicated.getPrice());
    }
}
