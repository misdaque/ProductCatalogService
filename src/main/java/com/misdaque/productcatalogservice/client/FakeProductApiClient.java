package com.misdaque.productcatalogservice.client;

import com.misdaque.productcatalogservice.dtos.FakeStoreProductDto;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class FakeProductApiClient implements IFakeProductApiClient{

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<FakeStoreProductDto> getAllProducts() {

        List<FakeStoreProductDto> fakeStoreProductDtos = new ArrayList<>();
        FakeStoreProductDto[] fakeStoreProductDtos1 =
                requestForEntity("https://fakestoreapi.com/products/", HttpMethod.GET, fakeStoreProductDtos, FakeStoreProductDto[].class)
                        .getBody();
        assert fakeStoreProductDtos1 != null;
        Collections.addAll(fakeStoreProductDtos, fakeStoreProductDtos1);

        return fakeStoreProductDtos;
    }

    @Override
    public FakeStoreProductDto getProductById(long id) {

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.GET, null, FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();

        if (fakeStoreProductDtoResponseEntity.getStatusCode().isSameCodeAs(HttpStatus.OK) && fakeStoreProductDto != null) {
            return fakeStoreProductDto;
        }

        return null;
    }

    @Override
    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto) {

        return requestForEntity("https://fakestoreapi.com/products", HttpMethod.POST, fakeStoreProductDto, FakeStoreProductDto.class).getBody();
    }

    @Override
    public FakeStoreProductDto replaceProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {

        FakeStoreProductDto fakeStoreProductDtoResponse =
                requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.PUT, fakeStoreProductDto, FakeStoreProductDto.class, id).getBody();
        assert fakeStoreProductDtoResponse != null;
        return fakeStoreProductDtoResponse;
    }

    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request,
                                                   Class<T> responseType, @Nullable Object... uriVariables) throws RestClientException {

        RestTemplate restTemplate = restTemplateBuilder.build();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
