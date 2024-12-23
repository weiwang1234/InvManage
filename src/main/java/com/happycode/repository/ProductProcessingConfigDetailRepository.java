package com.happycode.repository;

import com.happycode.model.ProductProcessingConfigDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductProcessingConfigDetailRepository extends JpaRepository<ProductProcessingConfigDetail, Long> {
    void deleteByProductid(Long productid);
    List<ProductProcessingConfigDetail> findByProductid(Long productid);


}
