package com.misdaque.productcatalogservice.client;

import com.misdaque.productcatalogservice.dtos.FakeStoreProductDto;
import java.util.List;

public interface IFakeProductApiClient {

    List<FakeStoreProductDto> getAllProducts();

    FakeStoreProductDto getProductById(long id);

    FakeStoreProductDto createProduct(FakeStoreProductDto product);

    FakeStoreProductDto replaceProduct(Long id, FakeStoreProductDto fakeStoreProductDto);
}
