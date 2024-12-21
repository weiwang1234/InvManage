package com.happycode.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseOrderSummaryExprot {
   // private Long ordetailid;
   private String orderparname;
   // private Long productid;
   private String productname;
    private Long quantity;
    private BigDecimal unitprice;

    // 符合查询字段顺序和类型的构造函数
    public PurchaseOrderSummaryExprot( String orderparname,
                                      String productname, Long quantity, BigDecimal unitprice) {
       // this.ordetailid = ordetailid;
        this.orderparname = orderparname;
       // this.productid = productid;
        this.productname = productname;
        this.quantity = quantity;
        this.unitprice = unitprice;
    }



}
