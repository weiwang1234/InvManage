package com.happycode.service;

import com.happycode.model.PurchaseOrderDetail;
import com.happycode.repository.PurchaseOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderDetailService {

    @Autowired
    private PurchaseOrderDetailRepository repository;

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
}
