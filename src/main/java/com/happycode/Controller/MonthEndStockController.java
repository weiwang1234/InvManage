package com.happycode.Controller;

import com.happycode.model.MonthEndStock;
import com.happycode.service.MonthEndStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/monthendstock")
public class MonthEndStockController {

    @Autowired
    private MonthEndStockService service;

    // 创建新的库存记录
    @PostMapping("/create")
    public MonthEndStock create(@RequestBody MonthEndStock monthEndStock) {
        return service.createMonthEndStock(monthEndStock);
    }

    // 更新库存记录
    @PutMapping("/update")
    public MonthEndStock update(@RequestBody MonthEndStock monthEndStock) {
        return service.updateMonthEndStock(monthEndStock);
    }

    // 删除库存记录
    @DeleteMapping("/delete/{productId}/{stockMonth}")
    public void delete(@PathVariable Long productId, @PathVariable String stockMonth) {
        service.deleteMonthEndStock(productId, stockMonth);
    }

    // 根据产品ID和盘点月份查询库存记录
    @GetMapping("/get/{productId}/{stockMonth}")
    public Optional<MonthEndStock> getByProductIdAndStockMonth(@PathVariable Long productId, @PathVariable String stockMonth) {
        return service.getMonthEndStock(productId, stockMonth);
    }

    // 根据盘点月份查询所有库存记录
    @GetMapping("/get/{stockMonth}")
    public List<MonthEndStock> getAllByStockMonth(@PathVariable String stockMonth) {
        return service.getAllByStockMonth(stockMonth);
    }
}
