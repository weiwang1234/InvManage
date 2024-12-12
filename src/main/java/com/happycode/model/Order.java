package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders_list")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;

    @Column(name = "orderparid")
    private String orderparid;

    @Column(name = "ordertotalamount")
    private String ordertotalamount;

    @Column(name = "orderdate")
    private String orderdate;
    @Column(name = "orderparname")
    private String orderparname;


    @Column(name = "orderdatetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime orderdatetime;
}
