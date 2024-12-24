package com.happycode.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductProcessingRequest {
    private ProductProcessing productprocessing;
    private List<ProductProcessingDetail> productprocessingdetail;

}
