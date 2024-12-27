package com.happycode.model.MonthEnd;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class PurchaseSummary {
    private long productId;

    private String productName;

    private Double totalQuantity=0.00;

    private BigDecimal totalUnitPrice =BigDecimal.ZERO;
    private Long id;
    public PurchaseSummary(long productId, String productName, Double totalQuantity, BigDecimal totalUnitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.totalUnitPrice = totalUnitPrice;
    }

}
