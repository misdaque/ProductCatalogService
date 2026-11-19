package com.misdaque.productcatalogservice.services;

import com.misdaque.productcatalogservice.dtos.FakeStoreProductDto;
import com.misdaque.productcatalogservice.dtos.ProductDto;
import com.misdaque.productcatalogservice.models.Category;
import com.misdaque.productcatalogservice.models.Product;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Product> getAllProducts() {

        RestTemplate restTemplate = restTemplateBuilder.build();
        List<Product> products = new ArrayList<>();
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForEntity("https://fakestoreapi.com/products/", FakeStoreProductDto[].class).getBody();

        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            Product product = from(fakeStoreProductDto);

            products.add(product);
        }

        return products;
    }

    @Override
    public Product getProductById(long id) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();

        if (fakeStoreProductDtoResponseEntity.getStatusCode().isSameCodeAs(HttpStatus.OK) && fakeStoreProductDto != null) {
            return from(fakeStoreProductDto);
        }

        return null;
    }

    @Override
    public Product createproduct(Product product) {

        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {

        FakeStoreProductDto fakeStoreProductDto = from(product);

        FakeStoreProductDto fakeStoreProductDtoResponse =
                requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.PUT, fakeStoreProductDto, FakeStoreProductDto.class, id).getBody();
        assert fakeStoreProductDtoResponse != null;
        return from(fakeStoreProductDtoResponse);
    }

    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request,
                                                   Class<T> responseType, @Nullable Object... uriVariables) throws RestClientException {

        RestTemplate restTemplate = restTemplateBuilder.build();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
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
