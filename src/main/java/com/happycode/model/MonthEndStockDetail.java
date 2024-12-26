package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "monthend_stockdetail")
public class MonthEndStockDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockdetailid;
    private Long productid;
    private String productname;
    private Double lastmonthinventory;
    private Double monthpurchases;
    private BigDecimal monthpurchasesamount;
    private BigDecimal monthsoldamount;
    private Double monthprocessedoutput;
    private Double monthsoldquantity;
    private Double monthprocessing;
    private String stockmonth;


}
