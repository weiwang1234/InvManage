package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "otherexpenses")
public class OtherExpenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otherexpensesid;

    private String otherexpensesname;
    private BigDecimal otherexpensesamount;
    private String otherexpensesremak;
    private String otherexpensesdate;

}
