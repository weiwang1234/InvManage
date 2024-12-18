package com.happycode.Controller;

import com.happycode.model.Inventory;
import com.happycode.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // 获取所有库存
    @PostMapping("/getAll")
    public List<Inventory> getAllInventories() {

        return inventoryService.getAllInventories("quantity", false);
    }

    // 根据库存ID获取单个库存
    @PostMapping("/getById")
    public ResponseEntity<Inventory> getInventoryById(@RequestParam Long id) {
        Optional<Inventory> inventory = inventoryService.getInventoryById(id);
        return inventory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 根据产品ID获取库存
    @PostMapping("/getByProductId")
    public ResponseEntity<Inventory> getInventoryByProductId(@RequestParam Long productid) {
        Inventory inventory = inventoryService.getInventoryByProductId(productid);
        return inventory != null ? ResponseEntity.ok(inventory) : ResponseEntity.notFound().build();
    }

}
