package com.happycode.service;

import com.happycode.model.MonthendStock;
import com.happycode.repository.MonthendStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonthendStockService {

    @Autowired
    private MonthendStockRepository monthendStockRepository;

    /**
     * 查询所有记录
     */
    public List<MonthendStock> getAllMonthendStocks() {
        return monthendStockRepository.findAll();
    }

    /**
     * 根据月份查询记录
     */
    public Optional<MonthendStock> getMonthendStockByMonth(String stockMonth) {
        return monthendStockRepository.findById(stockMonth);
    }

    /**
     * 新增记录
     */
    public MonthendStock addMonthendStock(MonthendStock monthendStock) {
        return monthendStockRepository.save(monthendStock);
    }

    /**
     * 有则更新无责插入
     */


    public MonthendStock updateMonthendStock(MonthendStock updatedStock) {
        if (updatedStock.getStockmonth() == null || updatedStock.getStockmonth().isEmpty()) {
            throw new IllegalArgumentException("Stockmonth cannot be null or empty");
        }

        Optional<MonthendStock> existingStock = monthendStockRepository.findById(updatedStock.getStockmonth());
        if (existingStock.isPresent()) {
            MonthendStock existing = existingStock.get();
            existing.setStockmonth(updatedStock.getStockmonth()); // 保留主键
            return monthendStockRepository.save(existing);
        } else {
            return monthendStockRepository.save(updatedStock);
        }
    }

    /**
     * 删除记录
     */
    public void deleteMonthendStock(String stockMonth) {
        if (monthendStockRepository.existsById(stockMonth)) {
            monthendStockRepository.deleteById(stockMonth);
        } else {
            throw new RuntimeException("记录不存在，无法删除");
        }
    }

    public Optional<MonthendStock> checkIfExists(MonthendStock stockmonth) {
        return monthendStockRepository.findById(stockmonth.getStockmonth());
    }

    public List<MonthendStock> findByStockmonthBetween(String startMonth, String endMonth) {
        return monthendStockRepository.findByStockmonthBetween(startMonth,endMonth);
    }
}