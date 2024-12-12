package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "orders_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordetailid;  // 订单明细id
    private Long orderid;   // 订单编号
    private String productid; // 产品编号
    private String productname; // 产品名称
    private Integer quantity; // 商品数量
    private BigDecimal unitprice; // 单价
}
