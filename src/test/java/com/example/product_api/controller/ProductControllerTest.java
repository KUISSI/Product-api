package com.example.product_api.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.product_api.model.Product;
import com.example.product_api.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ObjectMapper objectMapper;


@BeforeEach
void cleanDatabase() {
    repository.deleteAll();
}

@Test
void testGetById() throws Exception {
    Product p = new Product(null, "clavier", 10, List.of());
    Product saved = repository.save(p);

    mockMvc.perform(get("/products/" + saved.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("clavier"))
                .andExpect(jsonPath("$.price").value(10.0));
    }


    @Test
    void testCreateProduct() throws Exception {
        Product p = new Product(null, "clavier", 10, List.of());

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("clavier"));
    }

    @Test
    void testReadProduct() throws   Exception {
        Product p = repository.save(new Product(null, "clavier", 10, List.of()));

        mockMvc.perform(get("/products/" + p.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("clavier"))
                .andExpect(jsonPath("$.price").value(10.0));
    }


    @Test
    void testGetAll() throws Exception {
        Product p = new Product(null, "clavier", 10, List.of());
        repository.save(p);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("clavier"));
    }


    @Test
    void testUpdate() throws Exception {
        Product saved = repository.save(new Product(null, "Bic", 1.0, List.of()));
        Product updated = new Product(saved.getId(), "Crayon", 1.5, List.of());

        mockMvc.perform(put("/products/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Crayon"))
                .andExpect(jsonPath("$.price").value(1.5));
    }

    @Test
    void testDuplicate() throws Exception {
        Product saved = repository.save(new Product(null, "Stylo", 2.5, List.of()));

        mockMvc.perform(post("/products/" + saved.getId() + "/duplicate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Stylo"))
                .andExpect(jsonPath("$.price").value(2.5))
                .andExpect(jsonPath("$.id").isNumber());
    }

 

    @Test
    void testCreateBundle() throws Exception {
        Product p1 = repository.save(new Product(null, "Stylo", 2.5, List.of()));
        Product p2 = repository.save(new Product(null, "Crayon", 1.5, List.of()));
        List<Long> ids = List.of(p1.getId(), p2.getId());

        mockMvc.perform(post("/products/bundle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bundle: Stylo + Crayon"))
                .andExpect(jsonPath("$.price").value(4.0));
    }

   
}
