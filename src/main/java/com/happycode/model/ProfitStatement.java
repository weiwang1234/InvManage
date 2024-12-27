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
    private String profitmonth; // 月份 (主键)
    private BigDecimal purchasingexpenses; // 进货支出
    private BigDecimal otherexpenses; // 其他支出
    private BigDecimal salesrevenue; // 收入
    private BigDecimal netprofit ; // 净利润

}
