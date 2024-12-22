package com.happycode.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductProcessingConfigRequest {
    private ProductProcessingConfig productprocessingconfig;
    private List<ProductProcessingConfigDetail> productprocessingconfigdetail;
}
