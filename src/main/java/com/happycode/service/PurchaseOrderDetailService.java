package com.happycode.service;

import com.happycode.model.OrderDetailSummary;
import com.happycode.model.PurchaseOrder;
import com.happycode.model.PurchaseOrderDetail;
import com.happycode.model.SearchCriteria;
import com.happycode.repository.PurchaseOrderDetailRepository;
import com.happycode.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PurchaseOrderDetailService {

    @Autowired
    private PurchaseOrderDetailRepository repository;
    @Autowired
    private PurchaseOrderRepository purchaseorderrepository;
    @Autowired
    private InventoryService inventoryservice;



    public List<PurchaseOrderDetail> findAll() {
        return repository.findAll();
    }

    public PurchaseOrderDetail findById(Long ordetailid) {
        return repository.findById(ordetailid).orElse(null);
    }

    public PurchaseOrderDetail save(PurchaseOrderDetail purchaseOrderDetail) {
        return repository.save(purchaseOrderDetail);
    }

    public void deleteById(Long ordetailid) {
        repository.deleteById(ordetailid);
    }
    public List<PurchaseOrderDetail>  findByOrderid(Long orderId) {
       return   repository.findByOrderid(orderId);
    }


    @Transactional
    public void deletePurchaseOrderDetailAndUpdate(Long ordetailid) {
        //
        PurchaseOrderDetail detail = repository.findById(ordetailid)
                .orElseThrow(() -> new IllegalArgumentException("订单明细未找到，ID: "));

        //减少库存
        inventoryservice.updateInventory(detail.getProductid(), detail.getProductname(), detail.getQuantity(),"decrease");
        //更新主订单金额
        PurchaseOrder order = purchaseorderrepository.findById(detail.getOrderid())
                .orElseThrow(() -> new IllegalArgumentException("订单未找到 " ));

         List<PurchaseOrderDetail> detailList = repository.findByOrderid(detail.getOrderid());
         if (detailList.size()>1){
             order.setOrdertotalamount(order.getOrdertotalamount().subtract(detail.getUnitprice()));
             purchaseorderrepository.save(order);
         }else{

             purchaseorderrepository.deleteById(detail.getOrderid());
         }
         //删除明细
        repository.deleteById(ordetailid);

    }

    public List<OrderDetailSummary> getOrderDetailSummary(SearchCriteria request) {
        String startDate = request.getStartDate();  // 直接使用 String 类型的日期
        String endDate = request.getEndDate();      // 直接使用 String 类型的日期
        return repository.getOrderDetailSummary(request.getOrderparid(), startDate, endDate);
    }

}
