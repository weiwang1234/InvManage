package com.happycode.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailSummary {
    private String productName;
    private double quantity;
    private BigDecimal unitPrice;

    public OrderDetailSummary(String productName, double quantity, BigDecimal unitPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
}
