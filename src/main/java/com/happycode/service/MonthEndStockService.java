package com.happycode.service;

import com.happycode.model.MonthEndStock;
import com.happycode.repository.MonthEndStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MonthEndStockService {

    @Autowired
    private MonthEndStockRepository repository;

    // 增加新的库存记录
    @Transactional
    public MonthEndStock createMonthEndStock(MonthEndStock monthEndStock) {
        return repository.save(monthEndStock);
    }

    // 更新库存记录
    @Transactional
    public MonthEndStock updateMonthEndStock(MonthEndStock monthEndStock) {
        return repository.save(monthEndStock);
    }

    // 根据产品ID和盘点月份删除记录
    @Transactional
    public void deleteMonthEndStock(Long productId, String stockMonth) {
        repository.deleteById(productId);
    }

    // 根据产品ID和盘点月份查询库存记录
    public Optional<MonthEndStock> getMonthEndStock(Long productId, String stockMonth) {
        return repository.findByProductIdAndStockMonth(productId, stockMonth);
    }

    // 根据盘点月份查询所有库存记录
    public List<MonthEndStock> getAllByStockMonth(String stockMonth) {
        return repository.findByStockMonth(stockMonth);
    }

}
