package com.ecommerce.cara.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productDetails")
    private List<Cart> carts;

    @OneToMany(mappedBy = "productDetails")
    private List<OrderDetail> orderDetails;

    @Column
    private String color;

    @Column
    private String size;

}
