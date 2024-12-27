package com.happycode.Controller;

import com.happycode.model.ProfitStatement;
import com.happycode.model.SearchCriteria;
import com.happycode.service.ProfitStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profitstatement")
public class ProfitStatementController {

    @Autowired
    private ProfitStatementService profitStatementService;

    // 获取所有利润报表
    @GetMapping
    public List<ProfitStatement> getAllProfitStatements() {
        return profitStatementService.getAllProfitStatements();
    }

    // 根据月份获取利润报表
    @GetMapping("/{profitMonth}")
    public ResponseEntity<ProfitStatement> getProfitStatementByMonth(@PathVariable String profitMonth) {
        Optional<ProfitStatement> profitStatement = profitStatementService.getProfitStatementByMonth(profitMonth);
        return profitStatement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 新增或更新利润报表
    @PostMapping
    public ResponseEntity<ProfitStatement> saveOrUpdateProfitStatement(@RequestBody ProfitStatement profitStatement) {
        ProfitStatement savedProfitStatement = profitStatementService.saveOrUpdateProfitStatement(profitStatement);
        return ResponseEntity.ok(savedProfitStatement);
    }

    // 删除利润报表
    @DeleteMapping("/{profitMonth}")
    public ResponseEntity<Void> deleteProfitStatement(@PathVariable String profitMonth) {
        profitStatementService.deleteProfitStatement(profitMonth);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/querySearch")
    public List<ProfitStatement> getProfitStatements(@RequestBody SearchCriteria request) {
        return profitStatementService.findByProfitmonthBetween(request.getStartDate(), request.getEndDate());
    }
}
