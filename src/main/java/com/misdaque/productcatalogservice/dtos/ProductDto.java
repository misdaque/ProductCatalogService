package com.misdaque.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private long id;

    private String title;

    private String description;

    private Double amount;

    private String imageUrl;

    private CategoryDto category;
}
