package com.happycode.Controller;

import com.happycode.model.OrderDetailSummary;
import com.happycode.model.PurchaseOrderDetail;
import com.happycode.model.SearchCriteria;
import com.happycode.service.PurchaseOrderDetailService;
import com.happycode.utils.InsufficientStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Slf4j
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
    public ResponseEntity<String> deletePurchaseOrderDetail(@PathVariable Long id) {
        try {
            service.deletePurchaseOrderDetailAndUpdate(id);
            return ResponseEntity.ok("订单编号 " + id + " 已成功删除。");
        } catch (InsufficientStockException ex) {
            // 捕获库存不足异常，返回 400 错误
            log.error("删除订单明细失败，库存不足: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            // 捕获其他异常，返回 500 错误
            log.error("删除订单明细时发生服务器内部错误{}", ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误：" + ex.getMessage());
        }
    }

    @PostMapping("/getorderid/{id}")
    public List<PurchaseOrderDetail> findByOrderid(@PathVariable Long id) {
        return service.findByOrderid(id);
    }
    @PostMapping("/getOrderDetailSummary")
    public   List<OrderDetailSummary> getOrderDetailSummary(@RequestBody SearchCriteria searchcriteria) {
        return service.getOrderDetailSummary(searchcriteria);
    }
}
