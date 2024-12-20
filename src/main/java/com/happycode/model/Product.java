package com.happycode.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product_list")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productid;

    private String productname;
    private String productstatus;

}
