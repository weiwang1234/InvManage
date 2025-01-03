package com.happycode.Controller;
import com.alibaba.fastjson.JSONObject;
import com.happycode.model.MonthendStock;
import com.happycode.model.SearchCriteria;
import com.happycode.service.MonthendStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> getMonthendStockByStockmonth(@RequestBody String stockmonth) {
        JSONObject jsonObject = JSONObject.parseObject(stockmonth);  // 将 JSON 字符串转换为 JSONObject
        String stockMonth = jsonObject.getString("stockmonth");  // 提取 stockMonth 字段

        Optional<MonthendStock> monthendStock = monthendStockService.getMonthendStockByMonth(stockMonth);

        Map<String, Object> response = new HashMap<>();

        if (monthendStock.isPresent()) {
            // 如果找到了数据，返回数据并标记成功
            response.put("success", true);
            response.put("data", monthendStock.get());
            response.put("message", "");  // 空消息，表示没有错误
        } else {
            // 如果找不到数据，标记为失败，并返回提示信息
            response.put("success", false);
            response.put("data", null);
            response.put("message", "记录不存在");
        }

        return ResponseEntity.ok(response);  // 返回 200 状态，并包含 success, data, message 字段
    }

    @PostMapping("/querySearch")
    public List<MonthendStock> findByStockMonthBetween(@RequestBody SearchCriteria request) {
        return monthendStockService.findByStockmonthBetween(request.getStartDate(), request.getEndDate());
    }



}
