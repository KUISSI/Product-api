package com.example.product_api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> sources = new ArrayList<>();
    

@NotBlank
private String name;

@Positive
private Double price;


    // Basic getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public List<Product> getSources() {
        return sources;
    }
    
    public void setSources(List<Product> sources) {
        this.sources = sources;
    }

    // Constructeur par défaut requis par JPA
    public Product() {}

    // Nouveau constructeur avec tous les champs
    public Product(Long id, String name, double price, List<Product> sources) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sources = sources;
    }

    // Add Builder class
    public static class Builder {
        private Long id;
        private String name;
        private double price;
        private List<Product> sources = new ArrayList<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder sources(List<Product> sources) {
            this.sources = sources;
            return this;
        }

        public Product build() {
            return new Product(id, name, price, sources);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
