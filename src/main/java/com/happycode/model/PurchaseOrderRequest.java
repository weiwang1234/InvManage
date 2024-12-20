package com.happycode.model;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderRequest {
    private PurchaseOrder purchaseorder;
    private List<PurchaseOrderDetail> purchaseorderdetaillist;

    // Getters and Setters
}
