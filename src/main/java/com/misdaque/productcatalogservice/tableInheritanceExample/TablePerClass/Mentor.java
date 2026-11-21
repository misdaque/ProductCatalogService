package com.misdaque.productcatalogservice.tableInheritanceExample.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_mentor")
public class Mentor extends User {

    private Long hours;
}
