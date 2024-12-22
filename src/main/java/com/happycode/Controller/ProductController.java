package com.happycode.Controller;

import com.happycode.model.Product;
import com.happycode.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 获取所有产品
    @PostMapping("/getAll")
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 获取指定id的产品
    @PostMapping("/getById")
    public ResponseEntity<Product> getProductById(@RequestParam Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 添加新产品
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    // 更新产品
    @PostMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestParam Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 删除产品
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteProduct(@RequestBody Product product) {
        if (productService.deleteProduct(product.getProductid())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/getproduct/{productid}")
    public List<Product> getActiveProductsExcludingId(@PathVariable("productid") Long excludedId) {
        return productService.getActiveProductsExcludingId(excludedId);
    }
}
