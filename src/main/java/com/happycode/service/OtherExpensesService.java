package com.happycode.service;

import com.happycode.model.OtherExpenses;
import com.happycode.model.SearchCriteria;
import com.happycode.repository.OtherExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OtherExpensesService {

    @Autowired
    private OtherExpensesRepository repository;

    // 获取所有其他支出记录
    public List<OtherExpenses> getAllOtherExpenses() {
        return repository.findAll();
    }

    // 根据 ID 查找其他支出记录
    public Optional<OtherExpenses> getOtherExpensesById(Long id) {
        return repository.findById(id);
    }

    // 创建新的其他支出记录
    public OtherExpenses createOtherExpenses(OtherExpenses otherExpenses) {
        return repository.save(otherExpenses);
    }

    // 更新其他支出记录
    public OtherExpenses updateOtherExpenses(Long id, OtherExpenses otherExpenses) {
        if (repository.existsById(id)) {
            otherExpenses.setOtherexpensesid(id);
            return repository.save(otherExpenses);
        }
        return null; // 或者抛出异常
    }

    // 删除其他支出记录
    public void deleteOtherExpenses(Long id) {
        repository.deleteById(id);
    }

    public List<OtherExpenses> searchOtherExpenses(SearchCriteria request) {
        return repository.findByQuery(request.getStartDate(), request.getEndDate(), request.getName());
    }

    public BigDecimal findTotalOtherExpensesByMonth(String month) {
        return repository.findTotalOtherExpensesByMonth(month);
    }
    public List<OtherExpenses> findByOtherExpensesDate(String month) {
        return repository.findByOtherExpensesDate(month);
    }


}
