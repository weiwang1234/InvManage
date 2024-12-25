package com.happycode.repository;

import com.happycode.model.MonthEndStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthEndStockRepository extends JpaRepository<MonthEndStock, Long> {

    // 根据产品ID和盘点月份查询库存
    Optional<MonthEndStock> findByProductIdAndStockMonth(Long productId, String stockMonth);

    // 根据盘点月份查询所有数据
    List<MonthEndStock> findByStockMonth(String stockMonth);

}
