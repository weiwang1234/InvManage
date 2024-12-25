package com.happycode.repository;

import com.happycode.model.ProductProcessing;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductProcessingRepository extends JpaRepository<ProductProcessing, Long> {
    @Query("SELECT pd FROM ProductProcessing pd WHERE "
            + "(:startDate IS NULL OR :startDate = '' OR pd.processingdate >= :startDate) "
            + "AND (:endDate IS NULL OR :endDate = '' OR pd.processingdate <= :endDate) "
            + "AND (:productname IS NULL OR :productname = '' OR pd.productname LIKE %:name%)")
    List<ProductProcessing> findByConditions(@Param("startDate") String startDate,
                                             @Param("endDate") String endDate,
                                             @Param("name") String name);
}
