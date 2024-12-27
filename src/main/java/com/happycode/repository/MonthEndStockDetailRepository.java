package com.happycode.repository;

import com.happycode.model.MonthEndStockDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthEndStockDetailRepository extends JpaRepository<MonthEndStockDetail, Long> {

    // 根据产品ID和盘点月份查询库存
    Optional<MonthEndStockDetail> findByProductidAndAndStockmonth(Long productId, String stockMonth);

    // 根据盘点月份查询所有数据
    List<MonthEndStockDetail> findByStockmonth(String stockMonth);

    List<MonthEndStockDetail> findByStockmonthBetween(String startMonth, String endMonth);



}
