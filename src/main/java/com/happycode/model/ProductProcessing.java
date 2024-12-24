package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "productprocessing")
public class ProductProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long processingid;

    private Long productid;

    private String productname;

    private Double quantity;

    private String processingdate;




}
