package com.misdaque.productcatalogservice.services;

import com.misdaque.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();

    Product getProductById(long id);

    Product createproduct(Product product);

    Product replaceProduct(Long id, Product product);
}
