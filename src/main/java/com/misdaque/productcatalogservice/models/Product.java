package com.misdaque.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product extends BaseModel{

    private String title;

    private String description;

    private Double amount;

    private Category category;

    private String imageUrl;

    private Boolean isPrimeSpecific;
}
