package com.ecommerce.cara.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter @Getter
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandId;

    @Column
    private String brandName;

    @OneToMany(mappedBy = "brand")
    private List<Product> productList;
}
