package com.misdaque.productcatalogservice.controllers;

import com.misdaque.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts(){
        return null;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable(value = "id") Long productId){
        return null;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product){
        return null;
    }
}
