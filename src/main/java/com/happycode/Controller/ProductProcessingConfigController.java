package com.happycode.Controller;

import com.happycode.model.ProductProcessingConfig;
import com.happycode.model.ProductProcessingConfigDetail;
import com.happycode.service.ProductProcessingConfigDetailService;
import com.happycode.service.ProductProcessingConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.happycode.model.ProductProcessingConfigRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productprocessingconfig")
public class ProductProcessingConfigController {

    @Autowired
    private ProductProcessingConfigService service;
    @Autowired
    private ProductProcessingConfigDetailService productprocessingconfigdetailservice;
    // 创建配置 (POST 请求)
    @PostMapping("/create")
    public ResponseEntity<ProductProcessingConfig> createConfig(@RequestBody ProductProcessingConfigRequest productprocessingconfigrequest) {
        ProductProcessingConfig createdConfig = service.createconfiganddetail(productprocessingconfigrequest);
        return new ResponseEntity<>(createdConfig, HttpStatus.CREATED);
    }

    // 获取所有配置 (POST 请求)
    @PostMapping("/getAll")
    public ResponseEntity<List<ProductProcessingConfig>> getAllConfigs() {
        List<ProductProcessingConfig> configs = service.getAllConfigs();
        return new ResponseEntity<>(configs, HttpStatus.OK);
    }

    // 根据ID获取配置 (POST 请求)
    @PostMapping("/getById")
    public ResponseEntity<ProductProcessingConfig> getConfigById(@RequestBody Long id) {
        Optional<ProductProcessingConfig> config = service.getConfigById(id);
        return config.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 更新配置 (POST 请求)
    @PostMapping("/update")
    public ResponseEntity<ProductProcessingConfig> updateConfig(@RequestBody ProductProcessingConfig updatedConfig) {
        ProductProcessingConfig config = service.updateConfig(updatedConfig.getProcessingconfigid(), updatedConfig);
        return config != null ?
                new ResponseEntity<>(config, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 删除配置 (POST 请求)
    @PostMapping("/delete")
    public void deleteProductProcessingConfig(@RequestParam("processingconfigid") Long processingconfigid) {
        boolean isDeleted = service.deleteConfig(processingconfigid);
    }

    @PostMapping("/getDetails")
    public List<ProductProcessingConfigDetail> getProductDetails(@RequestParam("productid") Long productid) {
        // 根据 productId 查询产品加工配置详情
        return productprocessingconfigdetailservice.getConfigDetail(productid);
    }

    @PostMapping("/deleteDetail")
    public void deleteDetail(@RequestParam("configdetailid") Long configdetailid) {
        boolean isDeleted = productprocessingconfigdetailservice.deleteConfigDetail(configdetailid);
    }

    @PostMapping("/editdetail")
    public ResponseEntity<ProductProcessingConfigDetail> updateConfigDetail(@RequestBody ProductProcessingConfigDetail updatedConfigDetail) {
        ProductProcessingConfigDetail updatedProduct = productprocessingconfigdetailservice.updateConfigDetail(updatedConfigDetail);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct); // 返回更新后的产品配置
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 返回未找到的错误
        }
    }
}
