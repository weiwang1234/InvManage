package com.happycode.repository;

import com.happycode.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 通过产品 ID 查找库存
    Inventory findByProductid(Long productid);
}
