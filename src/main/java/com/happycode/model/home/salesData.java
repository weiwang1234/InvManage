package com.happycode.model.home;

import lombok.Data;
import com.happycode.model.Inventory;

import java.math.BigDecimal;
import java.util.List;

@Data
public class salesData {


    private BigDecimal dailySalesAmount; //当日销售额
    private BigDecimal monthlySalesAmount;//当月销售额
    private BigDecimal dailyPurchaseAmount;//当日进货额
    private BigDecimal monthlyPurchaseAmount;//当月进货额
    private List<SalesStatistics> salesStatistics;
    private List<Inventory>  Inventory;
}
