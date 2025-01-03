package com.happycode.model.home;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesStatistics {
    private String date; //
    private BigDecimal sales; //
    private BigDecimal purchase;

    public SalesStatistics(String month, BigDecimal totalSales, BigDecimal totalProch) {
        this.date = month;
        this.sales = totalSales;
        this.purchase = totalProch;
    }

}
