package com.happycode.Controller;

import com.happycode.model.*;
import com.happycode.service.ProductProcessingDetailService;
import com.happycode.service.ProductProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productprocessing")
public class ProductProcessingController {

    @Autowired
    private ProductProcessingService productProcessingService;
    @Autowired
    private ProductProcessingDetailService productprocessingdetailservice;

    // 查询所有产品加工记录
    @PostMapping("/getAll")
    public List<ProductProcessing> getAllProductProcessings() {
        return productProcessingService.getAllProductProcessings();
    }

    // 根据ID查询产品加工记录
    @PostMapping("/getById")
    public ResponseEntity<ProductProcessing> getProductProcessingById(@RequestBody Long id) {
        Optional<ProductProcessing> productProcessing = productProcessingService.getProductProcessingById(id);
        return productProcessing.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 新增产品加工记录
    @PostMapping("/add")
    public ProductProcessing addProductProcessing(@RequestBody ProductProcessingRequest productprocessingrequest) {
        return productProcessingService.addProductProcessing(productprocessingrequest);
    }

    // 修改产品加工记录
    @PostMapping("/update")
    public ResponseEntity<ProductProcessing> updateProductProcessing(@RequestBody ProductProcessing productProcessing) {
        ProductProcessing updatedProductProcessing = productProcessingService.updateProductProcessing(productProcessing.getProcessingid(), productProcessing);
        return updatedProductProcessing != null ? ResponseEntity.ok(updatedProductProcessing) : ResponseEntity.notFound().build();
    }

    // 删除产品加工记录
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteProductProcessing(@RequestBody Long id) {
        productProcessingService.deleteProductProcessing(id);
        return ResponseEntity.noContent().build();
    }

    //查看详情
    @PostMapping("/getproductdetail")
    public List<ProductProcessingDetail> getAllProductdetail(@RequestBody Long processingid) {
        return productprocessingdetailservice.getAllProductDetails(processingid);
    }

    @PostMapping("/getFilteredProductProcessing")
    public List<ProductProcessing> getFilteredProductProcessing(@RequestBody SearchCriteria request) {
        return productProcessingService.getFilteredProductProcessing(request);
    }

}
