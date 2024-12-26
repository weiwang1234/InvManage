package com.happycode.service;

import com.happycode.model.ProductProcessingDetail;
import com.happycode.repository.ProductProcessingDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductProcessingDetailService {

    @Autowired
    private ProductProcessingDetailRepository repository;

    // 查询所有记录
    public List<ProductProcessingDetail> getAllProductProcessingDetails() {
        return repository.findAll();
    }

    // 根据 ID 查询
    public Optional<ProductProcessingDetail> getProductProcessingDetailById(Long id) {
        return repository.findById(id);
    }

    // 添加新记录
    public ProductProcessingDetail createProductProcessingDetail(ProductProcessingDetail detail) {
        return repository.save(detail);
    }

    // 更新记录
    public ProductProcessingDetail updateProductProcessingDetail(Long id, ProductProcessingDetail detail) {
        if (repository.existsById(id)) {
            detail.setProcessingdetailid(id);
            return repository.save(detail);
        }
        return null;
    }

    // 删除记录
    public void deleteProductProcessingDetail(Long id) {
        repository.deleteById(id);
    }

    public List<ProductProcessingDetail> getAllProductDetails(Long processingid) {
        return repository.findByProcessingid(processingid);
    }

    public List<ProductProcessingDetail> getroductProcessingDetailSummary(String month) {
        return repository.getroductProcessingDetailSummary(month);
    }





}
