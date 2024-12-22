package com.happycode.service;

import com.happycode.model.ProductProcessingConfig;
import com.happycode.repository.ProductProcessingConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductProcessingConfigService {

    @Autowired
    private ProductProcessingConfigRepository repository;

    // 创建配置
    public ProductProcessingConfig createConfig(ProductProcessingConfig config) {
        return repository.save(config);
    }

    // 获取所有配置
    public List<ProductProcessingConfig> getAllConfigs() {
        return repository.findAll();
    }

    // 根据ID获取配置
    public Optional<ProductProcessingConfig> getConfigById(Long id) {
        return repository.findById(id);
    }

    // 更新配置
    public ProductProcessingConfig updateConfig(Long id, ProductProcessingConfig newConfig) {
        if (repository.existsById(id)) {
            newConfig.setProcessingconfigid(id);
            return repository.save(newConfig);
        }
        return null; // 如果配置不存在，则返回 null
    }

    // 删除配置
    public boolean deleteConfig(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false; // 如果配置不存在，则返回 false
    }
}
