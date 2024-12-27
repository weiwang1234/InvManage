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
    private Double lastmonthinventory=0.00;
    private Double monthpurchases=0.00;
    private BigDecimal monthpurchasesamount =BigDecimal.ZERO;
    private Double monthsoldquantity=0.00;
    private BigDecimal monthsoldamount=BigDecimal.ZERO;
    private Double monthprocessing=0.00;
    private Double monthprocessedoutput=0.00;
    private Double monthinventory=0.00;
    private String stockmonth;



}
