package com.happycode.repository;

import com.happycode.model.OrderDetailSummary;
import com.happycode.model.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Long> {

    List<PurchaseOrderDetail> findByOrderid(Long orderId);
    void deleteByOrderid(Long orderid); // 删除明细

    @Query("SELECT new com.happycode.model.OrderDetailSummary(p.productname, SUM(p.quantity), SUM(p.unitprice)) " +
            "FROM PurchaseOrderDetail p " +
            "WHERE (:orderparid IS NULL OR p.orderparid = :orderparid) " +
            "AND (:startDate IS NULL OR p.orderdate >= :startDate) " +
            "AND (:endDate IS NULL OR p.orderdate <= :endDate) " +
            "GROUP BY p.productname " +
            "ORDER BY SUM(p.unitprice) DESC")
    List<OrderDetailSummary> getOrderDetailSummary(@Param("orderparid") Long orderparid,
                                                   @Param("startDate") String startDate,
                                                   @Param("endDate") String endDate);



}
