package com.example.product_api.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    // private Product sample;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    // ✅ Test GET /products/1 - OK
    // @Test
    // void testGetById() throws Exception {
    //     when(repository.findById(1L)).thenReturn(Optional.of(sample));

    //     mockMvc.perform(get("/products/1"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name").value("Stylo"))
    //             .andExpect(jsonPath("$.price").value(2.5));
    // }

    // // ✅ Test GET /products/99 - Not Found
    // @Test
    // void testGetByIdNotFound() throws Exception {
    //     when(repository.findById(99L)).thenReturn(Optional.empty());

    //     mockMvc.perform(get("/products/99"))
    //             .andExpect(status().isNotFound());
    // }

    // ✅ Test POST /products - OK
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

    // // ✅ Test POST /products - Invalid input
    // @Test
    // void testCreateWithInvalidData() throws Exception {
    //     Product invalid = new Product(); // vide

    //     mockMvc.perform(post("/products")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(invalid)))
    //             .andExpect(status().isBadRequest());
    // }

    // // ✅ Test GET /products - All
    // @Test
    // void testGetAll() throws Exception {
    //     when(repository.findAll()).thenReturn(List.of(sample));

    //     mockMvc.perform(get("/products"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$[0].name").value("Stylo"));
    // }

    // // ✅ Test PUT /products/1 - OK
    // private String asJsonString(final Object obj) {
    //     try {
    //         return new ObjectMapper().writeValueAsString(obj);
    //     } catch (Exception e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    // @Test
    // void testUpdate() throws Exception {
    //     Product updated = new Product();
    //     updated.setId(1L);
    //     updated.setName("Crayon");
    //     updated.setPrice(1.5);

    //     when(repository.findById(1L)).thenReturn(Optional.of(sample));
    //     when(repository.save(any())).thenReturn(updated);

    //     mockMvc.perform(put("/products/1")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(updated)))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name").value("Crayon"))
    //             .andExpect(jsonPath("$.price").value(1.5));
    // }

    // @Test
    // void testDuplicate() throws Exception {
    //     Product clone = new Product();
    //     clone.setId(2L); // id différent
    //     clone.setName("Stylo");
    //     clone.setPrice(2.5);

    //     when(repository.findById(1L)).thenReturn(Optional.of(sample));
    //     when(repository.save(any())).thenReturn(clone);

    //     mockMvc.perform(post("/products/1/duplicate"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name").value("Stylo"))
    //             .andExpect(jsonPath("$.price").value(2.5))
    //             .andExpect(jsonPath("$.id").value(2)); // ID dupliqué attendu
    // }

    // @Test
    // void testDuplicateNotFound() throws Exception {
    //     when(repository.findById(99L)).thenReturn(Optional.empty());

    //     mockMvc.perform(post("/products/99/duplicate"))
    //             .andExpect(status().isNotFound());
    // }

    // @Test
    // void testCreateBundle() throws Exception {
    //     Product p1 = new Product(1L, "Stylo", 2.5, List.of());
    //     Product p2 = new Product(2L, "Crayon", 1.5, List.of());
    //     Product bundle = new Product(3L, "Bundle: Stylo + Crayon", 4.0, List.of(p1, p2));

    //     List<Long> ids = Arrays.asList(1L, 2L);

    //     when(repository.findAllById(ids)).thenReturn(Arrays.asList(p1, p2));
    //     when(repository.save(any())).thenReturn(bundle);

    //     mockMvc.perform(post("/products/bundle")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(ids)))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name").value("Bundle: Stylo + Crayon"))
    //             .andExpect(jsonPath("$.price").value(4.0));
    // }

    // @Test
    // void testCreateBundleEmptyList() throws Exception {
    //     List<Long> empty = List.of();

    //     mockMvc.perform(post("/products/bundle")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(empty)))
    //             .andExpect(status().isBadRequest());
    // }

    // @Test
    // void testDelete() throws Exception {
    //     // On suppose que le produit existe (pas obligatoire ici car on ne vérifie pas la présence)
    //     mockMvc.perform(delete("/products/1"))
    //             .andExpect(status().isOk());

    //     // Vérifie que la méthode du repository est bien appelée
    //     verify(repository, times(1)).deleteById(1L);
    // }

}
