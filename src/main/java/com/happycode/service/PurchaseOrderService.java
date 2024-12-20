package com.happycode.service;

import com.happycode.model.*;
import com.happycode.repository.PurchaseOrderDetailRepository;
import com.happycode.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository repository;
    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;



    public List<PurchaseOrder> findAll() {
        String today = LocalDate.now().toString(); // 转换为 yyyy-MM-dd 格式
        return repository.findByOrderDateOrInsertTime(today);
    }


    public List<PurchaseOrder> searchOrders(SearchCriteria criteria) {
        System.out.println(criteria.getStartDate()+criteria.getEndDate()+criteria.getCustomerName());
        return repository.findOrders(criteria.getStartDate(), criteria.getEndDate(), criteria.getCustomerName());
    }

    public PurchaseOrder findById(Long orderid) {
        return repository.findById(orderid).orElse(null);
    }

    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        return repository.save(purchaseOrder);
    }

    public void deleteById(Long orderid) {
        repository.deleteById(orderid);
    }

    @Transactional
    public void createOrder(PurchaseOrderRequest purchaseorderrequest) {
        // 保存订单主表
       ;
        PurchaseOrder savedOrder = repository.save( purchaseorderrequest.getPurchaseorder());


        // 设置订单编号到详情中并保存详情表
        for (PurchaseOrderDetail detail : purchaseorderrequest.getPurchaseorderdetaillist()) {
            detail.setOrderid(savedOrder.getOrderid());
            detail.setOrderparid(savedOrder.getOrderparid());
            detail.setOrderparname(savedOrder.getOrderparname());
            purchaseOrderDetailRepository.save(detail);
        }
    }
}
