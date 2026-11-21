package com.misdaque.productcatalogservice.tableInheritanceExample.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "sc_ta")
@DiscriminatorValue(value = "1")
public class Ta extends User {

    private Double ratings;
}
