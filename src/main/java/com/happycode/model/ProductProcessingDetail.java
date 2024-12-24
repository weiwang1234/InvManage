package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "productprocessing_detail")
public class ProductProcessingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long processingdetailid; // 加工输出id
    private Long processingid; // 加工输出id
    private Long productid; // 被加工产品id

    private String productname; // 被加工产品名称

    private Double productquantity; // 被加工数量

    private Long outputproductid; // 加工产出产品id

    private String outputproductname; // 加工产出产品名称

    private Double outputcount; // 产出数量

    private String processingdetaildate; // 加工产出时间






}
