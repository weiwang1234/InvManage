package com.happycode.repository;

import com.happycode.model.OrderDetail;
import com.happycode.model.OtherExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OtherExpensesRepository extends JpaRepository<OtherExpenses, Long> {
    @Query("SELECT o FROM OtherExpenses o " +
            "WHERE (:startDate IS NULL  OR :startDate = ''  OR o.otherexpensesdate >= :startDate) " +
            "AND (:endDate IS NULL  OR :endDate = ''  OR o.otherexpensesdate <= :endDate) " +
            "AND (:name IS NULL OR o.otherexpensesname LIKE %:name%)")
    List<OtherExpenses> findByQuery(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("name") String name
    );

    @Query("SELECT SUM(o.otherexpensesamount) FROM OtherExpenses o WHERE o.otherexpensesdate LIKE CONCAT(:month, '%')")
    BigDecimal findTotalOtherExpensesByMonth(@Param("month") String month);


    @Query("SELECT od FROM OtherExpenses od WHERE  od.otherexpensesdate  LIKE :otherexpensesdate")
    List<OtherExpenses> findByOtherExpensesDate(@Param("otherexpensesdate") String otherexpensesdate);

}
