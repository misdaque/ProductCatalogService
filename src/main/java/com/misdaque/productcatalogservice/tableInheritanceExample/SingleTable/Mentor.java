package com.misdaque.productcatalogservice.tableInheritanceExample.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "sc_mentor")
@DiscriminatorValue(value = "2")
public class Mentor extends User {

    private Long hours;
}
