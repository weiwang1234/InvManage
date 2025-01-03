package com.happycode.service;

import com.happycode.model.Inventory;
import com.happycode.model.home.SalesStatistics;
import com.happycode.model.home.salesData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class HomeService {
    @Autowired
    private OrderDetailService OrderDetailService;
    @Autowired
    private PurchaseOrderDetailService PurchaseOrderDetailService;

    @Autowired
    private InventoryService InventoryService;

    public salesData getHomepageSum(String orderDate) {

        Date currentDate = new Date();
        // 定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化当前时间
        String formattedDate = sdf.format(currentDate);
        BigDecimal dailySalesAmount =  OrderDetailService.findTotalSalesByOrderDate(formattedDate);
        BigDecimal monthlySalesAmount =  OrderDetailService.getSalesStatistics(formattedDate.substring(0, 7)+"%");
        BigDecimal dailyPurchaseAmount = PurchaseOrderDetailService.getTotalUnitPriceByOrderDate(formattedDate);
        BigDecimal monthlyPurchaseAmount =  PurchaseOrderDetailService.getTotalUnitPrice(formattedDate.substring(0, 7)+"%");
        List<Inventory>  Inventory =  InventoryService.getAllInventories("quantity",false);
        List<SalesStatistics> salesStatistics =  OrderDetailService.getSalesStatisticsByMonth(formattedDate.substring(0, 4)+"%");
        salesData salesData = new salesData();
        salesData.setDailySalesAmount(dailySalesAmount);
        salesData.setMonthlySalesAmount(monthlySalesAmount);
        salesData.setDailyPurchaseAmount(dailyPurchaseAmount);
        salesData.setMonthlyPurchaseAmount(monthlyPurchaseAmount);
        salesData.setInventory(Inventory);
        salesData.setSalesStatistics(salesStatistics);
        return  salesData;
    }
}
