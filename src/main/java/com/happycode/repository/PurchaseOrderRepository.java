package com.happycode.repository;

import com.happycode.model.Order;
import com.happycode.model.PurchaseOrder;
import com.happycode.model.PurchaseOrderSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query("SELECT o FROM PurchaseOrder o WHERE o.orderdate = :date OR SUBSTRING(o.inserttime, 1, 10) = :date")
    List<PurchaseOrder> findByOrderDateOrInsertTime(@Param("date") String date);
    @Query("SELECT o FROM PurchaseOrder o WHERE "
            + "(:startDate IS NULL OR o.orderdate >= :startDate) AND "
            + "(:endDate IS NULL OR o.orderdate <= :endDate) AND "
            + "(:customerName IS NULL OR o.orderparname LIKE %:customerName%)")
    List<PurchaseOrder> findOrders(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("customerName") String customerName
    );
    @Query("SELECT new com.happycode.model.PurchaseOrderSum(po.orderparid, po.orderparname, SUM(po.ordertotalamount)) " +
            "FROM PurchaseOrder po " +
            "WHERE (:orderparname IS NULL OR po.orderparname LIKE %:orderparname%) " +
            "AND (:startDate IS NULL OR po.orderdate >= :startDate) " +
            "AND (:endDate IS NULL OR po.orderdate <= :endDate) " +
            "GROUP BY po.orderparid, po.orderparname " +
            "ORDER BY SUM(po.ordertotalamount) DESC")
    List<PurchaseOrderSum> findPurchaseOrders(@Param("orderparname") String orderparid,
                                              @Param("startDate") String startDate,
                                              @Param("endDate") String endDate);


}


