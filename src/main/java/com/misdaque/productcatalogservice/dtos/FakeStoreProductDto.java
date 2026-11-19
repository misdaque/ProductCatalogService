package com.misdaque.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {

    private long id;

    private String title;

    private double price;

    private String description;

    private String imageUrl;

    private String category;
}
