package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "productprocessing_configdetail")
public class ProductProcessingConfigDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long configdetailid; // 产品配置id
    private Long productid; // 产品id
    private String productname; // 产品名称
    private Long opuputproudctid; // 产出产品id
    private String opuputproudctname; // 产出产品名称
    private String outputtype; // 产出类型（1比例,2个数）
    private Double outputcount; // 产出数值

    private Date insertime; // 录入时间

    private Date updatetime; // 更新时间

}
