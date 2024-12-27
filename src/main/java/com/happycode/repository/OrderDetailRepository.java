package com.happycode.repository;

import com.happycode.model.MonthEnd.OderDetailSummary;
import com.happycode.model.OrderDetail;
import com.happycode.model.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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


}
