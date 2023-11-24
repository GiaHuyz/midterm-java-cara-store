package com.ecommerce.cara.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    // Tạo quan hệ nhiều-một với bảng Customers
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "order_date")
    private Date orderDate;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}

