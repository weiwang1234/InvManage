package com.happycode.Controller;

import com.happycode.model.MonthEndStockDetail;
import com.happycode.model.MonthendStock;
import com.happycode.service.MonthEndStockDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/monthstockdetailstock")
public class MonthendStockDetailController {

    @Autowired
    private MonthEndStockDetailService service;

    // 创建新的库存记录
    @PostMapping("/create")
    public MonthEndStockDetail create(@RequestBody  MonthendStock monthendStock) {
        return service.createMonthEndStock(monthendStock);
    }

    // 更新库存记录
    @PutMapping("/update")
    public MonthEndStockDetail update(@RequestBody MonthEndStockDetail monthEndStock) {
        return service.updateMonthEndStock(monthEndStock);
    }

    // 删除库存记录
    @DeleteMapping("/delete/{productId}/{stockMonth}")
    public void delete(@PathVariable Long productId, @PathVariable String stockMonth) {
        service.deleteMonthEndStock(productId, stockMonth);
    }

    // 根据产品ID和盘点月份查询库存记录
    @GetMapping("/get/{productId}/{stockMonth}")
    public Optional<MonthEndStockDetail> getByProductIdAndStockMonth(@PathVariable Long productId, @PathVariable String stockMonth) {
        return service.getMonthEndStock(productId, stockMonth);
    }

    // 根据盘点月份查询所有库存记录
    @GetMapping("/get/{stockMonth}")
    public List<MonthEndStockDetail> getAllByStockMonth(@PathVariable String stockMonth) {
        return service.getAllByStockMonth(stockMonth);
    }
}
