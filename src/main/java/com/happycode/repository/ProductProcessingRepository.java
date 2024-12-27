package com.happycode.repository;

import com.happycode.model.MonthEnd.DeatilSummary;
import com.happycode.model.ProductProcessing;
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



    @Query("SELECT new com.happycode.model.MonthEnd.DeatilSummary(" +
            "p.productid, p.productname, SUM(p.quantity)) " +
            "FROM ProductProcessing p " +
            "WHERE p.processingdate LIKE CONCAT(:month, '%') " +
            "GROUP BY p.productid, p.productname")
    List<DeatilSummary> getProductProcessingSum(@Param("month") String month);

    @Query("SELECT od FROM ProductProcessing od WHERE od.processingdate LIKE :processingdate")
    List<ProductProcessing> findByProductProcessingDate(@Param("processingdate") String processingdate);

}


