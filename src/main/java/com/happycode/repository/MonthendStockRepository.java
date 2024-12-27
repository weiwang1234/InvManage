package com.happycode.repository;

import com.happycode.model.MonthEndStockDetail;
import com.happycode.model.MonthendStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthendStockRepository extends JpaRepository<MonthendStock, String> {
    // 默认的 CRUD 方法可直接使用
    List<MonthendStock> findByStockmonthBetween(String startMonth, String endMonth);

}
