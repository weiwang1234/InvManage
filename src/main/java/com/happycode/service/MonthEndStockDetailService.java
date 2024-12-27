package com.happycode.service;

import com.happycode.model.*;
import com.happycode.model.MonthEnd.DeatilSummary;
import com.happycode.model.MonthEnd.OderDetailSummary;
import com.happycode.model.MonthEnd.PurchaseSummary;
import com.happycode.repository.MonthEndStockDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
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
    @Autowired
    OtherExpensesService otherexpensesservice;
    @Autowired
    ProfitStatementService profitstatementservice;







    // 增加新的库存记录
    @Transactional
    public MonthEndStockDetail createMonthEndStock(MonthendStock monthendstock) {
        //插入月份表
        monthendstockservice.updateMonthendStock(monthendstock);
        //获取上个月份
        YearMonth currentMonth = YearMonth.parse(monthendstock.getStockmonth()); // 解析字符串为 YearMonth
         String  lastmonth   = currentMonth.minusMonths(1).toString(); // 获取上个月
        //上月库存
        List<MonthEndStockDetail> MonthEndStockDetails =   getAllByStockMonth(lastmonth);

        //获取盘点月份的进货信息
        List<PurchaseSummary> PurchaseOrderDetailS  = PurchaseOrderDetailService.getMonthlyPurchaseSummary(monthendstock.getStockmonth());
        //获取当月卖货信息
        List<OderDetailSummary>  OrderDetailS  = orderdetailservice.getMonthlyOrderSummary(monthendstock.getStockmonth());
        //获取当月二次被加工信息
        List<DeatilSummary>  ProductProcessingS = productprocessingservice.getProcessingSum(monthendstock.getStockmonth());

        //获取加工产出信息
        List<DeatilSummary>  ProductProcessingDetailS  = productprocessingdetailservice.getroductProcessingDetailSummary(monthendstock.getStockmonth());

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
            stockDetails.put(productId, stockDetail);
        }

        //处理被加工信息
        for (DeatilSummary deatilsummary : ProductProcessingS) {
            Long productId = deatilsummary.getProductid();
            MonthEndStockDetail stockDetail = stockDetails.getOrDefault(productId, new MonthEndStockDetail());
            stockDetail.setProductid(productId);
            stockDetail.setProductname(deatilsummary.getProductname());
            stockDetail.setMonthsoldquantity(deatilsummary.getQuantity()); //本月卖货数量
            stockDetail.setMonthsoldamount(deatilsummary.getUnitprice());//本月卖货金额
            stockDetails.put(productId, stockDetail);
        }

        //处理加工产出信息
        for (DeatilSummary deatilsummary : ProductProcessingDetailS) {
            Long productId = deatilsummary.getProductid();
            MonthEndStockDetail stockDetail = stockDetails.getOrDefault(productId, new MonthEndStockDetail());
            stockDetail.setProductid(productId);
            stockDetail.setProductname(deatilsummary.getProductname());
            stockDetail.setMonthsoldquantity(deatilsummary.getQuantity()); //本月卖货数量
            stockDetails.put(productId, stockDetail);
        }

        //处理上月库存信息
        for (MonthEndStockDetail monthendstockdetail : MonthEndStockDetails) {
            Long productId = monthendstockdetail.getProductid();
            MonthEndStockDetail stockDetail = stockDetails.getOrDefault(productId, new MonthEndStockDetail());
            stockDetail.setProductid(productId);
            stockDetail.setProductname(monthendstockdetail.getProductname());
            stockDetail.setLastmonthinventory(monthendstockdetail.getMonthinventory()); //
            stockDetails.put(productId, stockDetail);
        }




        for (MonthEndStockDetail stockDetail : stockDetails.values()) {

            double lastMonthInventory = stockDetail.getLastmonthinventory() != null ? stockDetail.getLastmonthinventory() : 0.0;
            double monthPurchases = stockDetail.getMonthpurchases() != null ? stockDetail.getMonthpurchases() : 0.0;
            double monthProcessing = stockDetail.getMonthprocessing() != null ? stockDetail.getMonthprocessing() : 0.0;
            double monthProcessedOutput = stockDetail.getMonthprocessedoutput() != null ? stockDetail.getMonthprocessedoutput() : 0.0;
            double monthSoldQuantity = stockDetail.getMonthsoldquantity() != null ? stockDetail.getMonthsoldquantity() : 0.0;


            double monthinventory =   lastMonthInventory+monthPurchases
                     -monthProcessing+monthProcessedOutput-monthSoldQuantity;

            stockDetail.setMonthinventory(monthinventory);
            stockDetail.setStockmonth(monthendstock.getStockmonth());

            Optional<MonthEndStockDetail> existingDetail = repository.findByProductidAndAndStockmonth(stockDetail.getProductid(),monthendstock.getStockmonth());

            if(existingDetail.isPresent()) {

                MonthEndStockDetail existing = existingDetail.get();
                //进货
                existing.setStockmonth(monthendstock.getStockmonth());
                existing.setProductid(existing.getProductid());
                existing.setProductname(existing.getProductname());
                existing.setMonthprocessing(existing.getMonthprocessing());
                existing.setMonthpurchasesamount(existing.getMonthpurchasesamount());
                //出货
                existing.setMonthsoldquantity(stockDetail.getMonthsoldquantity()); //本月卖货数量
                existing.setMonthsoldamount(stockDetail.getMonthsoldamount());//本月卖货金额
                //本月库存
                existing.setMonthinventory(monthinventory);
                repository.save(existing);
            }else {
                repository.save(stockDetail);
            }

        }

        //增加利润表
        //进货金额
        BigDecimal totalPurchasesAmount = stockDetails.values().stream()
                .map(detail -> detail.getMonthpurchasesamount() != null ? detail.getMonthpurchasesamount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSalesAmount = stockDetails.values().stream()
                .map(detail -> detail.getMonthsoldamount() != null ? detail.getMonthsoldamount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        //获取其他支出
        BigDecimal  otherAmount = otherexpensesservice.findTotalOtherExpensesByMonth(monthendstock.getStockmonth());
        otherAmount = otherAmount != null ? otherAmount : BigDecimal.ZERO;

        //获取当月的利润表
          ProfitStatement profitStatement = new ProfitStatement();
            profitStatement.setPurchasingexpenses(totalPurchasesAmount);
            profitStatement.setSalesrevenue(totalSalesAmount);
            profitStatement.setOtherexpenses(otherAmount);
            profitStatement.setNetprofit(totalSalesAmount.subtract(totalPurchasesAmount).subtract(otherAmount));
            profitStatement.setProfitmonth(monthendstock.getStockmonth());
        profitstatementservice.saveOrUpdateProfitStatement(profitStatement);
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
