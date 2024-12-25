package com.happycode.Controller;

import com.happycode.model.OtherExpenses;
import com.happycode.model.SearchCriteria;
import com.happycode.service.OtherExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/otherexpenses")
public class OtherExpensesController {

    @Autowired
    private OtherExpensesService service;

    // 获取所有其他支出记录
    @PostMapping("/getAll")
    public List<OtherExpenses> getAllOtherExpenses() {
        return service.getAllOtherExpenses();
    }

    // 根据 ID 获取其他支出记录
    @PostMapping("/getById")
    public ResponseEntity<OtherExpenses> getOtherExpensesById(@RequestBody Long id) {
        Optional<OtherExpenses> otherExpenses = service.getOtherExpensesById(id);
        return otherExpenses.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 创建新的其他支出记录
    @PostMapping("/create")
    public ResponseEntity<OtherExpenses> createOtherExpenses(@RequestBody OtherExpenses otherExpenses) {
        OtherExpenses created = service.createOtherExpenses(otherExpenses);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 更新其他支出记录
    @PostMapping("/update")
    public ResponseEntity<OtherExpenses> updateOtherExpenses(
            @RequestBody OtherExpenses otherExpenses) {
        // We need to include the ID in the request body for the update
        Long id = otherExpenses.getOtherexpensesid();
        OtherExpenses updated = service.updateOtherExpenses(id, otherExpenses);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // 删除其他支出记录
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteOtherExpenses(@RequestBody Long id) {
        service.deleteOtherExpenses(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<OtherExpenses>> searchOtherExpenses(@RequestBody SearchCriteria request) {

        List<OtherExpenses> results = service.searchOtherExpenses(request);
        return ResponseEntity.ok(results);
    }
}
