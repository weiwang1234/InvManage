package com.happycode.service;

import com.happycode.model.ProductProcessingConfigDetail;
import com.happycode.repository.ProductProcessingConfigDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductProcessingConfigDetailService {

    @Autowired
    private ProductProcessingConfigDetailRepository repository;

    // 创建新的配置
    public ProductProcessingConfigDetail createConfigDetail(ProductProcessingConfigDetail configDetail) {
        return repository.save(configDetail);
    }

    // 获取所有配置
    public List<ProductProcessingConfigDetail> getAllConfigDetails() {
        return repository.findAll();
    }

    // 根据ID获取配置
    public Optional<ProductProcessingConfigDetail> getConfigDetailById(Long id) {
        return repository.findById(id);
    }

    // 根据产品ID和产出产品ID查找配置


    // 更新配置
    public ProductProcessingConfigDetail updateConfigDetail(Long id, ProductProcessingConfigDetail updatedConfigDetail) {
        if (repository.existsById(id)) {
            updatedConfigDetail.setConfigdetailid(id);
            return repository.save(updatedConfigDetail);
        }
        return null;
    }

    // 删除配置
    public boolean deleteConfigDetail(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
