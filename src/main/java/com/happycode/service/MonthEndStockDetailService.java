package com.happycode.service;

import com.happycode.model.*;
import com.happycode.model.MonthEnd.OderDetailSummary;
import com.happycode.model.MonthEnd.PurchaseSummary;
import com.happycode.repository.MonthEndStockDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MonthEndStockDetailService {

    @Autowired
    private MonthEndStockDetailRepository repository;
    @Autowired
    MonthendStockService monthendstockservice;
    @Autowired
    PurchaseOrderDetailService PurchaseOrderDetailService;
    @Autowired
    OrderDetailService orderdetailservice;
    @Autowired
    ProductProcessingService productprocessingservice;
    @Autowired
    ProductProcessingDetailService productprocessingdetailservice;




    // 增加新的库存记录
    @Transactional
    public MonthEndStockDetail createMonthEndStock(MonthendStock monthendstock) {
        //插入月份表
        monthendstockservice.updateMonthendStock(monthendstock);
        //获取盘点月份的进货信息
        List<PurchaseSummary> PurchaseOrderDetailS  = PurchaseOrderDetailService.getMonthlyPurchaseSummary(monthendstock.getStockmonth());
        //获取当月卖货信息
        List<OderDetailSummary>  OrderDetailS  = orderdetailservice.getMonthlyOrderSummary(monthendstock.getStockmonth());
        //获取当月二次被加工信息
        List<ProductProcessing>  ProductProcessingS = productprocessingservice.getProcessingSum(monthendstock.getStockmonth());

        //获取加工产出信息
        List<ProductProcessingDetail>  ProductProcessingDetailS  = productprocessingdetailservice.getroductProcessingDetailSummary(monthendstock.getStockmonth());

        // 用于存储每个产品的库存明细
        Map<Long, MonthEndStockDetail> stockDetails = new HashMap<>();
        //处理进货数据
        for (PurchaseSummary purchase : PurchaseOrderDetailS) {
            Long productId = purchase.getProductId();
            MonthEndStockDetail stockDetail = stockDetails.getOrDefault(productId, new MonthEndStockDetail());
            stockDetail.setProductid(productId);
            stockDetail.setProductname(purchase.getProductName());
            stockDetail.setMonthpurchases(purchase.getTotalQuantity()); //本月进货数量
            stockDetail.setMonthpurchasesamount(purchase.getTotalUnitPrice());//本月进货金额
            stockDetail.setStockmonth(monthendstock.getStockmonth());
            stockDetails.put(productId, stockDetail);
        }
        //处理销货数据
        for (OderDetailSummary OderDetail : OrderDetailS) {
            Long productId = OderDetail.getProductid();
            MonthEndStockDetail stockDetail = stockDetails.getOrDefault(productId, new MonthEndStockDetail());
            stockDetail.setProductid(productId);
            stockDetail.setProductname(OderDetail.getProductname());
            stockDetail.setMonthsoldquantity(OderDetail.getQuantity()); //本月卖货数量
            stockDetail.setMonthsoldamount(OderDetail.getUnitprice());//本月卖货金额
            stockDetail.setStockmonth(monthendstock.getStockmonth());
            stockDetails.put(productId, stockDetail);
        }



        for (MonthEndStockDetail stockDetail : stockDetails.values()) {
            Optional<MonthEndStockDetail> existingDetail = repository.findByProductidAndAndStockmonth(stockDetail.getProductid(),monthendstock.getStockmonth());
            if(existingDetail.isPresent()) {

                MonthEndStockDetail existing = existingDetail.get();
                //进货
                existing.setStockmonth(stockDetail.getStockmonth());
                existing.setProductid(existing.getProductid());
                existing.setProductname(existing.getProductname());
                existing.setMonthprocessing(existing.getMonthprocessing());
                existing.setMonthpurchasesamount(existing.getMonthpurchasesamount());
                //出货
                existing.setMonthsoldquantity(stockDetail.getMonthsoldquantity()); //本月卖货数量
                existing.setMonthsoldamount(stockDetail.getMonthsoldamount());//本月卖货金额
                repository.save(existing);
            }else {
                repository.save(stockDetail);
            }

        }
        MonthEndStockDetail a = new  MonthEndStockDetail();
       return  a;
    }

    // 更新库存记录
    @Transactional
    public MonthEndStockDetail updateMonthEndStock(MonthEndStockDetail monthEndStock) {
        return repository.save(monthEndStock);
    }

    // 根据产品ID和盘点月份删除记录
    @Transactional
    public void deleteMonthEndStock(Long productId, String stockMonth) {
        repository.deleteById(productId);
    }

    // 根据产品ID和盘点月份查询库存记录
    public Optional<MonthEndStockDetail> getMonthEndStock(Long productId, String stockMonth) {
        return repository.findByProductidAndAndStockmonth(productId, stockMonth);
    }

    // 根据盘点月份查询所有库存记录
    public List<MonthEndStockDetail> getAllByStockMonth(String stockMonth) {
        return repository.findByStockmonth(stockMonth);
    }

}
