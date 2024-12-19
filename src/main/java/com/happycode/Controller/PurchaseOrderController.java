package com.happycode.Controller;

import com.happycode.model.Order;
import com.happycode.model.PurchaseOrder;
import com.happycode.model.SearchCriteria;
import com.happycode.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchaseorders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService service;

    @PostMapping("/list")
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return service.findAll();
    }

    @PostMapping("/searchOrders")
    public List<PurchaseOrder> searchOrders(@RequestBody SearchCriteria criteria) {
        return service.searchOrders(criteria);
    }


    @PostMapping("/get/{id}")
    public PurchaseOrder getPurchaseOrderById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/add")
    public PurchaseOrder addPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return service.save(purchaseOrder);
    }

    @PostMapping("/update")
    public PurchaseOrder updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return service.save(purchaseOrder);
    }

    @PostMapping("/delete/{id}")
    public String deletePurchaseOrder(@PathVariable Long id) {
        service.deleteById(id);
        return "Purchase order with ID " + id + " has been deleted.";
    }
}
