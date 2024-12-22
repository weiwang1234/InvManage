package com.happycode.repository;


import com.happycode.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    // 可以根据需求定义更多自定义查询方法
    List<Product> findByProductstatus(String partnerstatus);
    List<Product> findByProductstatusAndProductidNot(String productstatus, Long excludedId);


}
