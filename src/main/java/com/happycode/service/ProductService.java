package com.happycode.service;

import com.happycode.model.Product;
import com.happycode.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 获取所有产品
    public Iterable<Product> getAllProducts() {
        return  productRepository.findAll();
    }

    // 根据id获取产品
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 根据id获取产品
    public List<Product> findByProductstatus() {
        return productRepository.findByProductstatus("1");
    }

    // 添加新产品
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // 更新产品
    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setProductid(id);
            return productRepository.save(product);
        }
        return null; // 如果找不到产品，返回 null
    }

    // 删除产品
    public boolean TakeeffectPartner(Long id) {

        Optional<Product> productOpt = getProductById(id);

        if (productOpt.isPresent()) {
            Product foundPartner = productOpt.get();

            // 修改 status 字段为 "2"（表示软删除）
            foundPartner.setProductstatus("1");

            // 保存修改后的合作方对象
            productRepository.save(foundPartner);
            return true;
        }

        return false; // 如果找不到产品，返回 false

    }


    public boolean deleteProduct(Long id) {

        Optional<Product> productOpt = getProductById(id);

        if (productOpt.isPresent()) {
            Product foundPartner = productOpt.get();

            // 修改 status 字段为 "2"（表示软删除）
            foundPartner.setProductstatus("2");

            // 保存修改后的合作方对象
            productRepository.save(foundPartner);
            return true;
        }

        return false; // 如果找不到产品，返回 false

    }

    public List<Product> getActiveProductsExcludingId(Long excludedId) {
        return productRepository.findByProductstatusAndProductidNot("1", excludedId);
    }
}
