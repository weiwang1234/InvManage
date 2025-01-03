package com.happycode.repository;

import com.happycode.model.MonthEnd.OderDetailSummary;
import com.happycode.model.OrderDetail;
import com.happycode.model.home.SalesStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // 可以根据订单编号查询订单明细
    void deleteByOrderid(Long orderid);

    List<OrderDetail> findByOrderid(Long orderId);
    @Query("SELECT new com.happycode.model.MonthEnd.OderDetailSummary(" +
            "o.productid, o.productname, SUM(o.quantity), SUM(o.unitprice)) " +
            "FROM OrderDetail o " +
            "WHERE o.orderdate LIKE CONCAT(:month, '%') " +
            "GROUP BY o.productid, o.productname")
    List<OderDetailSummary> getMonthlyOrderSummary(@Param("month") String month);

    @Query("SELECT od FROM OrderDetail od WHERE od.orderdate LIKE :orderDate")
    List<OrderDetail> findByOrderDate(@Param("orderDate") String orderDate);
    //当日销售总金额
    @Query("SELECT SUM(o.unitprice) FROM OrderDetail o WHERE o.orderdate = :orderDate")
    BigDecimal findTotalSalesByOrderDate(String orderDate);
    //当月销售总金额
    @Query("SELECT SUM(od.unitprice) " +
            "FROM OrderDetail od WHERE od.orderdate LIKE :orderDatePattern")
    BigDecimal getSalesStatistics(@Param("orderDatePattern") String orderDatePattern);


    @Query("SELECT new com.happycode.model.home.SalesStatistics(" +
            "DATE_FORMAT(od.orderdate, '%Y-%m'), " +
            "SUM(od.unitprice), " +
            "SUM(pd.unitprice)) " +
            "FROM OrderDetail od " +
            "JOIN PurchaseOrderDetail pd ON pd.orderdate = od.orderdate " +
            "WHERE od.orderdate LIKE :orderDatePattern " +
            "GROUP BY DATE_FORMAT(od.orderdate, '%Y-%m')")
    List<SalesStatistics> getSalesStatisticsByMonth(@Param("orderDatePattern") String orderDatePattern);





}
