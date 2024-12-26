package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "monthend_stock")
public class MonthendStock {
    @Id
    @Column(name = "stockmonth", nullable = false)
    private String stockmonth;




}
