package com.misdaque.productcatalogservice.services;

import com.misdaque.productcatalogservice.client.IFakeProductApiClient;
import com.misdaque.productcatalogservice.dtos.FakeStoreProductDto;
import com.misdaque.productcatalogservice.models.Category;
import com.misdaque.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IFakeProductApiClient fakeProductApiClient;

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();
        List<FakeStoreProductDto> fakeStoreProductDtos = fakeProductApiClient.getAllProducts();

        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            Product product = from(fakeStoreProductDto);
            products.add(product);
        }

        return products;
    }

    @Override
    public Product getProductById(long id) {

        FakeStoreProductDto fakeStoreProductDto =
                fakeProductApiClient.getProductById(id);

        if (fakeStoreProductDto != null) {
            return from(fakeStoreProductDto);
        }

        return null;
    }

    @Override
    public Product createproduct(Product product) {

        FakeStoreProductDto fakeStoreProductDto = from(product);

        FakeStoreProductDto fakeStoreProductDtoResponse = fakeProductApiClient.createProduct(fakeStoreProductDto);

        return from(fakeStoreProductDtoResponse);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {

        FakeStoreProductDto fakeStoreProductDto = from(product);
        FakeStoreProductDto fakeStoreProductDtoResponse =
                fakeProductApiClient.replaceProduct(id, fakeStoreProductDto);
        return from(fakeStoreProductDtoResponse);
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();

        product.setAmount(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setImageUrl(fakeStoreProductDto.getImageUrl());

        Category category = new Category();

        category.setName(fakeStoreProductDto.getCategory());

        product.setCategory(category);

        return product;
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();

        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setImageUrl(product.getImageUrl());
        fakeStoreProductDto.setPrice(product.getAmount());

        if (product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }
}
