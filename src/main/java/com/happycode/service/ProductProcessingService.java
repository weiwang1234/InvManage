package com.happycode.service;

import com.happycode.model.MonthEnd.DeatilSummary;
import com.happycode.model.ProductProcessing;
import com.happycode.model.ProductProcessingDetail;
import com.happycode.model.ProductProcessingRequest;
import com.happycode.model.SearchCriteria;
import com.happycode.repository.ProductProcessingDetailRepository;
import com.happycode.repository.ProductProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductProcessingService {

    @Autowired
    private ProductProcessingRepository productProcessingRepository;
    @Autowired
    private ProductProcessingDetailRepository productprocessingdetailrepository;
    @Autowired
    private InventoryService inventoryservice;

    @Autowired
    private ProductProcessingDetailService productprocessingdetailservice;


    // 新增
    @Transactional
    public ProductProcessing addProductProcessing(ProductProcessingRequest request) {
        //减少库存
        inventoryservice.updateInventory(request.getProductprocessing().getProductid(), request.getProductprocessing().getProductname(), request.getProductprocessing().getQuantity(), "decrease");
        ProductProcessing saveproductprocessing = productProcessingRepository.save(request.getProductprocessing());
        for (ProductProcessingDetail detail : request.getProductprocessingdetail()) {
            detail.setProcessingid(saveproductprocessing.getProcessingid());
            detail.setProductid(request.getProductprocessing().getProductid()); // 将主配置ID赋值给详情
            detail.setProductname(request.getProductprocessing().getProductname());
            detail.setProductquantity(request.getProductprocessing().getQuantity());
            detail.setProcessingdetaildate(request.getProductprocessing().getProcessingdate());
            productprocessingdetailrepository.save(detail);
            inventoryservice.updateInventory(detail.getOutputproductid(), detail.getOutputproductname(), detail.getOutputcount(), "increase");
        }

        return saveproductprocessing;
    }

    // 查询所有
    public List<ProductProcessing> getAllProductProcessings() {
        return productProcessingRepository.findAll();
    }

    // 根据ID查询
    public Optional<ProductProcessing> getProductProcessingById(Long id) {
        return productProcessingRepository.findById(id);
    }

    // 修改
    public ProductProcessing updateProductProcessing(Long id, ProductProcessing productProcessing) {
        if (productProcessingRepository.existsById(id)) {
            productProcessing.setProcessingid(id);
            return productProcessingRepository.save(productProcessing);
        }
        return null; // 或者抛出异常
    }

    // 删除
    @Transactional
    public void deleteProductProcessing(Long id) {
        if (productProcessingRepository.existsById(id)) {
            List<ProductProcessingDetail> ProcessingDetai = productprocessingdetailservice.getAllProductDetails(id);
            for (ProductProcessingDetail detail : ProcessingDetai) {
                productprocessingdetailservice.deleteProductProcessingDetail(detail.getProcessingdetailid());
                inventoryservice.updateInventory(detail.getOutputproductid(), detail.getOutputproductname(), detail.getOutputcount(), "decrease");
            }
            productProcessingRepository.deleteById(id);
        }
    }

    //getCustomerName 为产品名称
    public List<ProductProcessing> getFilteredProductProcessing(SearchCriteria filter) {
        return productProcessingRepository.findByConditions(filter.getStartDate(), filter.getEndDate(), filter.getName());

    }

    public List<DeatilSummary> getProcessingSum(String  month) {
        return productProcessingRepository.getProductProcessingSum(month);

    }

    public List<ProductProcessing> findByProductProcessingDate(String month) {
        return productProcessingRepository.findByProductProcessingDate(month);
    }

}