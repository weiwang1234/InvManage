package com.happycode.Controller;

import com.happycode.model.ProductProcessingConfig;
import com.happycode.service.ProductProcessingConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product-processing-config")
public class ProductProcessingConfigController {

    @Autowired
    private ProductProcessingConfigService service;

    // 创建配置 (POST 请求)
    @PostMapping("/create")
    public ResponseEntity<ProductProcessingConfig> createConfig(@RequestBody ProductProcessingConfig config) {
        ProductProcessingConfig createdConfig = service.createConfig(config);
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
    public ResponseEntity<Void> deleteConfig(@RequestBody Long id) {
        boolean deleted = service.deleteConfig(id);
        return deleted ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
