package com.happycode.service;

import com.happycode.model.ProfitStatement;
import com.happycode.repository.ProfitStatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfitStatementService {

    @Autowired
    private ProfitStatementRepository profitStatementRepository;

    // 新增或更新利润报表
    public ProfitStatement saveOrUpdateProfitStatement(ProfitStatement profitStatement) {
        return profitStatementRepository.save(profitStatement);
    }

    // 获取所有利润报表
    public List<ProfitStatement> getAllProfitStatements() {
        return profitStatementRepository.findAll();
    }

    // 根据月份查询利润报表
    public Optional<ProfitStatement> getProfitStatementByMonth(String profitMonth) {
        return profitStatementRepository.findById(profitMonth);
    }

    // 删除指定月份的利润报表
    public void deleteProfitStatement(String profitMonth) {
        profitStatementRepository.deleteById(profitMonth);
    }
}
