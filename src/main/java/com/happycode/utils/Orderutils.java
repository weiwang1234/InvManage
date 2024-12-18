package com.happycode.utils;

import com.happycode.model.Inventory;
import com.happycode.model.OrderDetail;
import com.happycode.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Orderutils {

    // 假设你有一个销售订单对象（SalesOrder）和订单明细对象（SalesOrderDetail）
    @Autowired
    private InventoryRepository inventoryRepository;

    public void updateInventoryForSale(List<OrderDetail> orderdetail) {
        for (OrderDetail detail : orderdetail) {
            long productId = detail.getProductid();
            int quantitySold = detail.getQuantity();

            // 获取当前库存
            Inventory inventory = inventoryRepository.findByProductid(productId);

            if (inventory != null && inventory.getQuantity() >= quantitySold) {
                // 更新库存
                inventory.setQuantity(inventory.getQuantity() - quantitySold);
                inventoryRepository.save(inventory);
            } else {
                throw new InsufficientStockException("库存不足，无法完成订单！");
            }
        }
    }
// 假设你有一个进货订单对象（PurchaseOrder）和订单明细对象（PurchaseOrderDetail）

//    public void updateInventoryForPurchase(PurchaseOrder purchaseOrder) {
//        for (PurchaseOrderDetail detail : purchaseOrder.getOrderDetails()) {
//            long productId = detail.getProductId();
//            int quantityPurchased = detail.getQuantity();
//
//            // 获取当前库存
//            Inventory inventory = inventoryRepository.findByProductId(productId);
//
//            if (inventory != null) {
//                // 更新库存
//                inventory.setQuantity(inventory.getQuantity() + quantityPurchased);
//                inventoryRepository.save(inventory);
//            } else {
//                // 如果库存中没有该产品，新增库存记录
//                Inventory newInventory = new Inventory();
//                newInventory.setProductId(productId);
//                newInventory.setProductName(detail.getProductName());
//                newInventory.setQuantity(quantityPurchased);
//                inventoryRepository.save(newInventory);
//            }
//        }
//    }




}
