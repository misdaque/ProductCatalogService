package com.misdaque.productcatalogservice.tableInheritanceExample.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "sc_instructor")
@DiscriminatorValue(value = "3")
public class Instructor extends User {

    private String company;
}
