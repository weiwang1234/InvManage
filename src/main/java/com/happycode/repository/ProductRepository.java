package com.happycode.repository;

import com.happycode.entity.Product;
import com.happycode.model.Partner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    // 可以根据需求定义更多自定义查询方法
    List<Product> findByProductstatus(String partnerstatus);

}
