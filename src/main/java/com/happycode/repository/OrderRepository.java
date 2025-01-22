package com.happycode.repository;

import com.happycode.model.Order;
import com.happycode.model.home.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 可以根据需要自定义查询方法
    @Query("SELECT o FROM Order o WHERE o.orderdate = :date OR SUBSTRING(o.inserttime, 1, 10) = :date")
    List<Order> findByOrderDateOrInsertTime(@Param("date") String date);

    @Query("SELECT o FROM Order o WHERE "
            + "(:startDate IS NULL OR o.orderdate >= :startDate) AND "
            + "(:endDate IS NULL OR o.orderdate <= :endDate) AND "
            + "(:customerName IS NULL OR o.orderparname LIKE %:customerName%)")
    List<Order> findOrders(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("customerName") String customerName
    );
    @Query("SELECT new com.happycode.model.home.Maintenance(ol.orderid,ol.orderparname,ol.orderdate,pl.partnerphone) FROM Order ol JOIN Partner pl ON ol.orderparid = pl.partnerid " +
            "WHERE ol.maintenance = '1' AND ol.reminder = '1' " +
            "AND DATEDIFF(:targetDate, STR_TO_DATE(ol.orderdate, '%Y-%m-%d')) > :daysDifference")
    List<Maintenance> findOrdersByMaintenanceReminderAndDateDifference(@Param("targetDate") String targetDate, @Param("daysDifference") int daysDifference);
}
