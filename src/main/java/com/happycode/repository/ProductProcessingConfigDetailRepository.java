package com.happycode.repository;

import com.happycode.model.ProductProcessingConfigDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductProcessingConfigDetailRepository extends JpaRepository<ProductProcessingConfigDetail, Long> {
    // 如果需要，根据 productid 和 opuputproudctid 查找唯一的配置
}
