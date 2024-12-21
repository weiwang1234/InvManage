package com.happycode.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseOrderSum {
    private Long orderparid;
    private String orderparname;
    private BigDecimal ordertotalamountsum;

    // 确保有一个构造函数，JPA 会使用它来映射查询结果
    public PurchaseOrderSum(Long orderparid, String orderparname, BigDecimal ordertotalamountsum) {
        this.orderparid = orderparid; // 如果需要 String 类型，可以转换
        this.orderparname = orderparname;
        this.ordertotalamountsum = ordertotalamountsum;
    }
}
