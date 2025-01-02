package com.happycode.Controller;
import com.alibaba.fastjson.JSONObject;
import com.happycode.model.MonthendStock;
import com.happycode.model.SearchCriteria;
import com.happycode.service.MonthendStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/monthendstock")
public class MonthendStockController {

    @Autowired
    private MonthendStockService monthendStockService;

    // 获取所有的 MonthendStock
    @PostMapping("/getAll")
    public List<MonthendStock> getAllMonthendStocks() {
        return monthendStockService.getAllMonthendStocks();
    }

    // 根据 stockmonth 获取 MonthendStock
    @PostMapping("/getByStockmonth")
    public ResponseEntity<MonthendStock> getMonthendStockByStockmonth(@RequestBody String stockmonth) {
        JSONObject jsonObject = JSONObject.parseObject(stockmonth);  // 将 JSON 字符串转换为 JSONObject
        String stockMonth = jsonObject.getString("stockmonth");  // 提取 stockMonth 字段
        Optional<MonthendStock> monthendStock = monthendStockService.getMonthendStockByMonth(stockMonth);
        return monthendStock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/querySearch")
    public List<MonthendStock> findByStockMonthBetween(@RequestBody SearchCriteria request) {
        return monthendStockService.findByStockmonthBetween(request.getStartDate(), request.getEndDate());
    }



}
