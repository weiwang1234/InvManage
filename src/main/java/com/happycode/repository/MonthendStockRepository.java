package com.happycode.repository;

import com.happycode.model.MonthendStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthendStockRepository extends JpaRepository<MonthendStock, String> {
    // 默认的 CRUD 方法可直接使用
}
