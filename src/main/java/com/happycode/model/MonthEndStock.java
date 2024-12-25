package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "monthend_stock")
public class MonthEndStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private Double lastMonthInventory;

    private Double monthPurchases;

    private Double monthPurchasesAmount;

    private Double monthProcessedOutput;

    private Double monthSoldQuantity;

    private Double monthSoldAmount;

    private String stockMonth;


}
