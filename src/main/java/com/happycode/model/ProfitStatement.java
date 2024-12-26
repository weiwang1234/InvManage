package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@Entity
@Table(name = "profitstatement")
public class ProfitStatement {
    @Id
    @Column(name = "profitmonth", nullable = false, length = 100)
    private String profitMonth; // 月份 (主键)
    private BigDecimal purchasingExpenses; // 进货支出
    private BigDecimal otherExpenses; // 其他支出
    private BigDecimal salesRevenue; // 收入



}
