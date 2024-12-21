package com.happycode.service;

import com.happycode.model.*;
import com.happycode.repository.PurchaseOrderDetailRepository;
import com.happycode.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository repository;
    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;
    @Autowired
    private InventoryService inventoryservice;




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
        PurchaseOrder savedOrder = repository.save( purchaseorderrequest.getPurchaseorder());


        // 设置订单编号到详情中并保存详情表
        for (PurchaseOrderDetail detail : purchaseorderrequest.getPurchaseorderdetaillist()) {
            detail.setOrderid(savedOrder.getOrderid());
            detail.setOrderparid(savedOrder.getOrderparid());
            detail.setOrderparname(savedOrder.getOrderparname());
            purchaseOrderDetailRepository.save(detail);
            //添加成功更新库存
            inventoryservice.updateInventory(detail.getProductid(),detail.getProductname(),detail.getQuantity(),"increase");
        }

    }

    @Transactional
    public void deleteOrderWithDetails(Long orderId) {
        // 查询所有与订单关联的明细记录
        List<PurchaseOrderDetail> orderDetails = purchaseOrderDetailRepository.findByOrderid(orderId);
        // 恢复库存
        for (PurchaseOrderDetail detail : orderDetails) {
            inventoryservice.updateInventory(detail.getProductid(),detail.getProductname(),detail.getQuantity(),"decrease");
        }
        // 删除订单明细
        purchaseOrderDetailRepository.deleteByOrderid(orderId);
        // 删除主订单
        repository.deleteById(orderId);
    }

    public List<PurchaseOrderSum> getPurchaseOrderssum(SearchCriteria searchcriteria) {
        return repository.findPurchaseOrders(searchcriteria.getCustomerName(),
                searchcriteria.getStartDate(),
                searchcriteria.getEndDate());
    }
}
