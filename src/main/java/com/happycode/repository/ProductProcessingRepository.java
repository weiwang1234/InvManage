package com.happycode.repository;

import com.happycode.model.ProductProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductProcessingRepository extends JpaRepository<ProductProcessing, Long> {
}
