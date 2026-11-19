package com.misdaque.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryDto {

    private long id;

    private String name;

    private String description;

    private List<ProductDto> products;
}
