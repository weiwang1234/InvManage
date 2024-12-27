package com.happycode.repository;

import com.happycode.model.MonthEnd.DeatilSummary;
import com.happycode.model.OrderDetail;
import com.happycode.model.ProductProcessingConfig;
import com.happycode.model.ProductProcessingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductProcessingDetailRepository extends JpaRepository<ProductProcessingDetail, Long> {
    // 可自定义查询方法，如果需要根据字段查找，可以在此添加
    List<ProductProcessingDetail> findByProcessingid(Long processingid);

    @Query("select new com.happycode.model.MonthEnd.DeatilSummary( outputproductid ,outputproductname ,sum(outputcount) ) from ProductProcessingDetail    where  " +
            " processingdetaildate  like CONCAT(:month, '%')  group by   outputproductid ,outputproductname ")
    List<DeatilSummary> getroductProcessingDetailSummary(@Param("month") String month);


}
