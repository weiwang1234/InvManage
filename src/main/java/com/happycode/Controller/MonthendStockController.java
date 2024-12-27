package com.happycode.Controller;
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
        Optional<MonthendStock> monthendStock = monthendStockService.getMonthendStockByMonth(stockmonth);
        return monthendStock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/querySearch")
    public List<MonthendStock> findByStockMonthBetween(@RequestBody SearchCriteria request) {
        return monthendStockService.findByStockmonthBetween(request.getStartDate(), request.getEndDate());
    }


}
