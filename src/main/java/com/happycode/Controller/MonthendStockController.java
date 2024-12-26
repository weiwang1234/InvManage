package com.happycode.Controller;

import com.happycode.model.MonthendStock;
import com.happycode.service.MonthendStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("monthendstock")
public class MonthendStockController {

    @Autowired
    private MonthendStockService monthendStockService;

    /**
     * 检查记录是否存在
     * @param monthendstock 盘点月份
     * @return ResponseEntity 包含是否存在的信息
     */
    @PostMapping("/check")
    public ResponseEntity<?> checkIfExists(@RequestBody MonthendStock monthendstock) {

        System.out.println("Received stockMonth: " + monthendstock.getStockmonth());  // 打印接收到的值

        Optional<MonthendStock> existingRecord = monthendStockService.checkIfExists(monthendstock);

        if (existingRecord.isPresent()) {
            return ResponseEntity.ok(existingRecord.get()); // 返回已有记录
        }
        return ResponseEntity.ok("记录不存在"); // 提示记录不存在
    }

    /**
     * 新增记录
     * @param monthendStock 盘点记录
     * @return 新增后的记录
     */
    @PostMapping("/add")
    public ResponseEntity<MonthendStock> addMonthendStock(@RequestBody MonthendStock monthendStock) {
        return ResponseEntity.ok(monthendStockService.addMonthendStock(monthendStock));
    }



}
