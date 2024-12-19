package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchaseorder_detail")
@Data
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordetailid;

    private Long orderid;

    private Long orderparid;

    private String orderparname;

    private String productid;

    private String productname;

    private Integer quantity;

    private BigDecimal unitprice;

    private String orderdate;



}
