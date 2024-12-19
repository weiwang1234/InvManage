package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "purchaseorder_list")
@Data
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;

    private String orderparid;

    private String orderparname;

    private BigDecimal ordertotalamount;

    private String orderdate;

    @Column(name = "inserttime", updatable = false, insertable = false)
    private String inserttime;


}
