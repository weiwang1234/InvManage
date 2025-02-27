package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "orders_detail")
public  class  OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordetailid;  // 订单明细id
    private Long orderid;   // 订单编号
    private Long productid; // 产品编号
    private String productname; // 产品名称
    private double quantity; // 商品数量
    private BigDecimal unitprice; // 单价
    private String orderdate; // 单价
    private Long orderparid; // 订单用户id
    private String orderparname; // 订单用户名称

}
