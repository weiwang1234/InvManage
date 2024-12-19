package com.happycode.Controller;

import com.happycode.model.PurchaseOrderDetail;
import com.happycode.service.PurchaseOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchaseorderdetails")
public class PurchaseOrderDetailController {

    @Autowired
    private PurchaseOrderDetailService service;

    @PostMapping("/list")
    public List<PurchaseOrderDetail> getAllPurchaseOrderDetails() {
        return service.findAll();
    }

    @PostMapping("/get/{id}")
    public PurchaseOrderDetail getPurchaseOrderDetailById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/add")
    public PurchaseOrderDetail addPurchaseOrderDetail(@RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        return service.save(purchaseOrderDetail);
    }

    @PostMapping("/update")
    public PurchaseOrderDetail updatePurchaseOrderDetail(@RequestBody PurchaseOrderDetail purchaseOrderDetail) {
        return service.save(purchaseOrderDetail);
    }

    @PostMapping("/delete/{id}")
    public String deletePurchaseOrderDetail(@PathVariable Long id) {
        service.deleteById(id);
        return "Purchase order detail with ID " + id + " has been deleted.";
    }
}
