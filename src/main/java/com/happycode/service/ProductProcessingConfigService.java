package com.happycode.service;

import com.happycode.model.ProductProcessingConfig;
import com.happycode.model.ProductProcessingConfigRequest;
import com.happycode.repository.ProductProcessingConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import com.happycode.repository.ProductProcessingConfigDetailRepository;
import com.happycode.model.ProductProcessingConfigDetail;
@Service
public class ProductProcessingConfigService {

    @Autowired
    private ProductProcessingConfigRepository repository;
    @Autowired
    private ProductProcessingConfigDetailRepository ConfigDetailrepository;


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

    // 删除配置及关联信息
    @Transactional
    public boolean deleteConfig(Long id) {
        if (repository.existsById(id)) {
            Optional<ProductProcessingConfig> proconfig =   repository.findById(id);
            repository.deleteById(id);
            ConfigDetailrepository.deleteByProductid(proconfig.get().getProductid());
            return true;
        }
        return false; // 如果配置不存在，则返回 false
    }
    @Transactional
    public ProductProcessingConfig createconfiganddetail(ProductProcessingConfigRequest request) {

        List<ProductProcessingConfig> configList = repository.findByProductid(request.getProductprocessingconfig().getProductid());
        ProductProcessingConfig savedConfig  =null;
        Long  productid = null;
        String  productname  ="";
        if(configList.size()<1){
            savedConfig = repository.save(request.getProductprocessingconfig());
            productid = savedConfig.getProductid();
            productname = savedConfig.getProductname();
        }else{
            productid=request.getProductprocessingconfig().getProductid();
            productname=request.getProductprocessingconfig().getProductname();

        }

        // 保存详情配置
        for (ProductProcessingConfigDetail detail : request.getProductprocessingconfigdetail()) {
            detail.setProductid(productid); // 将主配置ID赋值给详情
            detail.setProductname(productname);
            ConfigDetailrepository.save(detail);
        }
        return savedConfig;
    }

    public void deleteByProductid(Long productid) {
        // 删除所有与该产品相关的配置
        List<ProductProcessingConfig> configs = repository.findByProductid(productid);
        if (!configs.isEmpty()) {
            repository.deleteAll(configs);  // 批量删除
        }
    }
}

