package com.misdaque.productcatalogservice.controllers;

import com.misdaque.productcatalogservice.dtos.CategoryDto;
import com.misdaque.productcatalogservice.dtos.ProductDto;
import com.misdaque.productcatalogservice.models.Category;
import com.misdaque.productcatalogservice.models.Product;
import com.misdaque.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts(){

        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();

        if (!products.isEmpty()){
            for (Product product : products) {
                ProductDto productDto = from(product);
                productDtos.add(productDto);
            }

            return new ResponseEntity<>(productDtos,  HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable(value = "id") Long productId){
        try {

            if (productId < 1)
                throw new RuntimeException("Invalid product id");

            Product product = productService.getProductById(productId);

            if (product == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(from(product), HttpStatus.OK);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){

        Product product = from(productDto);

        productService.createproduct(product);

        return new  ResponseEntity<>(from(product), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto){

        Product productRequest = from(productDto);

        Product product = productService.replaceProduct(id, productRequest);

        return new  ResponseEntity<>(from(product), HttpStatus.OK);
    }

    private ProductDto from(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setAmount(product.getAmount());
        productDto.setDescription(product.getDescription());
        productDto.setTitle(product.getTitle());
        productDto.setImageUrl(product.getImageUrl());

        if(product.getCategory() != null){
            Category category = product.getCategory();
            CategoryDto categoryDto = new CategoryDto();

            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setDescription(category.getDescription());

            productDto.setCategory(categoryDto);
        }

        return productDto;
    }

    private Product from(ProductDto productDto){
        Product product = new Product();

        product.setId(productDto.getId());
        product.setAmount(productDto.getAmount());
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setImageUrl(productDto.getImageUrl());
        if(productDto.getCategory() != null){
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }

        return product;
    }
}
