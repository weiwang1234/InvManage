package com.happycode.model.MonthEnd;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeatilSummary {

    private long productid;
    private String productname;
    private Double quantity=0.00;
    private BigDecimal unitprice=BigDecimal.ZERO;
    public DeatilSummary(long productid, String productname, Double quantity, BigDecimal unitprice) {
        this.productid = productid;
        this.productname = productname;
        this.quantity = quantity;
        this.unitprice = unitprice;
    }
    public DeatilSummary(long productid, String productname, Double quantity) {
        this.productid = productid;
        this.productname = productname;
        this.quantity = quantity;
        this.unitprice = unitprice;
    }
}
