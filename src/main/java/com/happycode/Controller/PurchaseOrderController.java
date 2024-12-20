package com.happycode.Controller;

import com.happycode.model.Order;
import com.happycode.model.PurchaseOrder;
import com.happycode.model.PurchaseOrderRequest;
import com.happycode.model.SearchCriteria;
import com.happycode.service.PurchaseOrderService;
import com.happycode.utils.InsufficientStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String addPurchaseOrder(@RequestBody PurchaseOrderRequest purchaseorderrequest) {
         service.createOrder(purchaseorderrequest);
        return "订单添加成功";
    }

    @PostMapping("/update")
    public PurchaseOrder updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return service.save(purchaseOrder);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deletePurchaseOrder(@PathVariable Long id) {
        try {
            service.deleteOrderWithDetails(id);
            return ResponseEntity.ok("订单编号 " + id + " 已成功删除。");
        } catch (InsufficientStockException ex) {
            // 捕获库存不足异常，返回 400 错误
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            // 捕获其他异常，返回 500 错误
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误：" + ex.getMessage());
        }
    }

}
