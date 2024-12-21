package com.happycode.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailSummary {
    private String productName;
    private Long quantity;
    private BigDecimal unitPrice;

    public OrderDetailSummary(String productName, Long quantity, BigDecimal unitPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
}
