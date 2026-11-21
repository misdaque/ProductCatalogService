package com.misdaque.productcatalogservice.tableInheritanceExample.MappedSuperClass;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {

    private String email;

    @Id
    private Long id;
}
