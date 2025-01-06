package com.happycode.model.home;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesStatistics {
    private String date; //
    private BigDecimal sales=BigDecimal.ZERO; //
    private BigDecimal purchase=BigDecimal.ZERO;

    public SalesStatistics(String month, BigDecimal totalSales, BigDecimal totalProch) {
        this.date = month;
        this.sales = totalSales;
        this.purchase = totalProch;
    }

    public SalesStatistics(String month, BigDecimal totalsum,String  type) {
        this.date = month;
        if("1".equals(type)){
            this.sales = totalsum;
        }else{
            this.purchase = totalsum;
        }
    }



}
