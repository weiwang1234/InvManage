package com.happycode.service;

import com.happycode.model.ProductProcessingConfigDetail;
import com.happycode.repository.ProductProcessingConfigDetailRepository;
import com.happycode.repository.ProductProcessingConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductProcessingConfigDetailService {

    @Autowired
    private ProductProcessingConfigDetailRepository repository;
    @Autowired
    private ProductProcessingConfigService productprocessingconfigservice;



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
    public ProductProcessingConfigDetail updateConfigDetail(ProductProcessingConfigDetail updatedConfigDetail) {
        if (repository.existsById(updatedConfigDetail.getConfigdetailid())) {
            Optional<ProductProcessingConfigDetail> existingProduct = repository.findById(updatedConfigDetail.getConfigdetailid());
            if (existingProduct.isPresent()) {
                ProductProcessingConfigDetail product = existingProduct.get();
                product.setOutputtype(updatedConfigDetail.getOutputtype());
                product.setOutputcount(updatedConfigDetail.getOutputcount());
                // 保存更新后的产品配置
                return repository.save(product);
            }
        }
        return null;
    }

    // 删除配置
    @Transactional
    public boolean deleteConfigDetail(Long id) {
        if (repository.existsById(id)) {

            Optional<ProductProcessingConfigDetail>  ConfigDetail =   getConfigDetailById(id);
            List<ProductProcessingConfigDetail> ConfigDetailList  = repository.findByProductid(ConfigDetail.get().getProductid());
            if(ConfigDetailList.size()<=1){
                productprocessingconfigservice.deleteByProductid(ConfigDetail.get().getProductid());
            }
            repository.deleteById(id);

            return true;
        }
        return false;
    }
    //获取详情
    public List<ProductProcessingConfigDetail>  getConfigDetail(Long id) {
        return  repository.findByProductid(id);
    }


}
