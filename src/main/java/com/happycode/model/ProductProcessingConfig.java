package com.happycode.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "productprocessing_config")
public class ProductProcessingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long processingconfigid;
    @Column(name = "productid")
    private Long productid;

    private String productname;

}
