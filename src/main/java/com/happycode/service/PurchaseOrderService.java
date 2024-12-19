package com.happycode.service;

import com.happycode.model.Order;
import com.happycode.model.PurchaseOrder;
import com.happycode.model.SearchCriteria;
import com.happycode.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository repository;

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
}
