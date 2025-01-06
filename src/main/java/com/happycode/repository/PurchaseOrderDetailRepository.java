package com.happycode.repository;

import com.happycode.model.MonthEnd.PurchaseSummary;
import com.happycode.model.OrderDetail;
import com.happycode.model.OrderDetailSummary;
import com.happycode.model.PurchaseOrderDetail;
import com.happycode.model.PurchaseOrderSummaryExprot;
import com.happycode.model.home.SalesStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
            "GROUP BY p.productname,p.productid " +
            "ORDER BY SUM(p.unitprice) DESC")
    List<OrderDetailSummary> getOrderDetailSummary(@Param("orderparid") Long orderparid,
                                                   @Param("startDate") String startDate,
                                                   @Param("endDate") String endDate);

    //导出功能
    @Query("SELECT new com.happycode.model.PurchaseOrderSummaryExprot( " +
            " pd.orderparname, pd.productname, SUM(pd.quantity), SUM(pd.unitprice)) " +
            "FROM PurchaseOrderDetail pd " +
            "WHERE (:startDate IS NULL OR pd.orderdate >= :startDate) " +
            "AND (:endDate IS NULL OR pd.orderdate < :endDate) " +
            "AND (:customerName IS NULL OR pd.orderparname LIKE %:customerName%) " +
            "GROUP BY pd.orderparid, pd.orderparname, pd.productid, pd.productname")
    List<PurchaseOrderSummaryExprot> findOrderDetailsExprot(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("customerName") String customerName);

    @Query("SELECT new com.happycode.model.MonthEnd.PurchaseSummary(" +
            "p.productid, p.productname, SUM(p.quantity), SUM(p.unitprice)) " +
            "FROM PurchaseOrderDetail p " +
            "WHERE p.orderdate LIKE CONCAT(:month, '%') " +
            "GROUP BY p.productid, p.productname")
    List<PurchaseSummary> getMonthlyPurchaseSummary(@Param("month") String month);

    @Query("SELECT od FROM PurchaseOrderDetail od WHERE od.orderdate LIKE :orderDate")
    List<PurchaseOrderDetail> findByOrderDate(@Param("orderDate") String orderDate);


    @Query("SELECT SUM(pd.unitprice) " +
            "FROM PurchaseOrderDetail pd WHERE pd.orderdate = :orderDate")
    BigDecimal getTotalUnitPriceByOrderDate(@Param("orderDate") String orderDate);


    @Query("SELECT SUM(pd.unitprice) FROM PurchaseOrderDetail pd WHERE pd.orderdate LIKE :orderDatePattern")
    BigDecimal getTotalUnitPrice(@Param("orderDatePattern") String orderDatePattern);


    @Query("SELECT new com.happycode.model.home.SalesStatistics(" +
            "substring(od.orderdate,1,7) , " +
            "SUM(od.unitprice), " +
            " '2' )" +
            "FROM  PurchaseOrderDetail od " +
            "WHERE od.orderdate LIKE :orderDatePattern " +
            "group  by substring(od.orderdate,1,7) ")
    List<SalesStatistics> getSalesStatisticsByMonth(@Param("orderDatePattern") String orderDatePattern);



}




