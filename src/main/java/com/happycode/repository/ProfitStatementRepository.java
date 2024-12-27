package com.happycode.repository;

import com.happycode.model.ProfitStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfitStatementRepository extends JpaRepository<ProfitStatement, String> {
    // 可以定义自定义查询方法
    List<ProfitStatement> findByProfitmonthBetween(String startMonth, String endMonth);

}
