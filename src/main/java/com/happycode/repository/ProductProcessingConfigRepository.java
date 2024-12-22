package com.happycode.repository;

import com.happycode.model.ProductProcessingConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductProcessingConfigRepository extends JpaRepository<ProductProcessingConfig, Long> {
    // 这里可以根据需要定义自定义查询方法
}
