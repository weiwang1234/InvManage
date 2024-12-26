package com.happycode.model.MonthEnd;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OderDetailSummary {
    private long productid;

    private String productname;

    private Double quantity;

    private BigDecimal unitprice;
    
    public OderDetailSummary(long productid, String productname, Double quantity, BigDecimal unitprice) {
        this.productid = productid;
        this.productname = productname;
        this.quantity = quantity;
        this.unitprice = unitprice;
    }
}
