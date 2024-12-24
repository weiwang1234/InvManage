package com.happycode.service;

import com.happycode.model.Inventory;
import com.happycode.repository.InventoryRepository;
import com.happycode.utils.InsufficientStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // 获取所有库存

    public List<Inventory> getAllInventories(String sortField, boolean ascending) {
        // 根据 ascending 决定升序还是降序
        Sort sort = ascending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        return inventoryRepository.findAll(sort);
    }


    // 根据 ID 获取单个库存
    public Optional<Inventory> getInventoryById(Long inventoryid) {
        return inventoryRepository.findById(inventoryid);
    }

    // 根据 productid 获取库存
    public Inventory getInventoryByProductId(Long productid) {
        return inventoryRepository.findByProductid(productid);
    }

    // 添加新的库存
    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    // 更新库存
    public Inventory updateInventory(Long inventoryid, Inventory inventoryDetails) {
        // 使用 Optional 来处理空值
        Inventory inventory = inventoryRepository.findById(inventoryid)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id " + inventoryid)); // 使用 RuntimeException 作为异常类

        inventory.setProductid(inventoryDetails.getProductid());
        inventory.setProductname(inventoryDetails.getProductname());
        inventory.setQuantity(inventoryDetails.getQuantity());
        return inventoryRepository.save(inventory);
    }

    // 删除库存
    public void deleteInventory(Long inventoryid) {
        Inventory inventory = inventoryRepository.findById(inventoryid)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id " + inventoryid)); // 使用 RuntimeException 作为异常类
        inventoryRepository.delete(inventory);
    }

    //-----------------------------------------------------------新添加工具方法---------------------------------------------

    public void updateInventory(Long productId, String productName,double quantity, String operation) {
        Optional<Inventory> optionalInventory = Optional.ofNullable(inventoryRepository.findByProductid(productId));
        Inventory inventory;
        if (optionalInventory.isPresent()) {
            inventory = optionalInventory.get();
        } else {
            inventory = new Inventory();
            inventory.setProductid(productId);
            inventory.setProductname(productName);
            inventory.setQuantity(0); // 初始库存为0
        }

        if ("increase".equalsIgnoreCase(operation)) {
            inventory.setQuantity(inventory.getQuantity() + quantity); // 增加库存
        } else if ("decrease".equalsIgnoreCase(operation)) {
            if (inventory.getQuantity() < quantity) {
                throw new InsufficientStockException(
                         "["+productName+"]库存不足,无法删除 "
                );
            }
            inventory.setQuantity(inventory.getQuantity() - quantity); // 减少库存
        } else {
            throw new IllegalArgumentException("无效的操作类型: " + operation);
        }

        inventoryRepository.save(inventory);
    }



}
