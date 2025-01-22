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
    private double ordertotalamount;

    @Column(name = "orderdate")
    private String orderdate;

    @Column(name = "orderparname")
    private String orderparname;

    @Column(name = "maintenance")
    private String maintenance;

    @Column(name = "reminder")
    private String reminder="1";

    @Column(name = "inserttime", updatable = false, insertable = false)
    private String inserttime;



}
