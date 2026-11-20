package com.misdaque.productcatalogservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Product extends BaseModel{

    private String title;

    private String description;

    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    private String imageUrl;

    private Boolean isPrimeSpecific;
}
