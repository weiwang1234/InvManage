package com.happycode.repository;

import com.happycode.model.ProfitStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitStatementRepository extends JpaRepository<ProfitStatement, String> {
    // 可以定义自定义查询方法
}
