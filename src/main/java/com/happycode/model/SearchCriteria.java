package com.happycode.model;

import lombok.Data;
@Data
public class SearchCriteria {
    private String startDate; // 开始日期 (格式 yyyy-MM-dd)
    private String endDate;   // 结束日期 (格式 yyyy-MM-dd)
    private String customerName; // 客户姓名

}
