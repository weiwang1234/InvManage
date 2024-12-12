package com.happycode.Controller;

import com.happycode.model.Order;
import com.happycode.model.OrderDetail;
import com.happycode.model.OrderRequest;
import com.happycode.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;



    // 获取所有订单
    @PostMapping("/getAll")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // 获取单个订单
    @PostMapping("/getById")
    public ResponseEntity<Order> getOrderById(@RequestParam("id") Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 创建订单
    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        Order newOrder = orderService.addOrder(order);
        return ResponseEntity.ok(newOrder);
    }

    // 更新订单
    @PostMapping("/update")
    public ResponseEntity<Order> updateOrder(@RequestParam("id") Long id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(id, order);
        if (updatedOrder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedOrder);
    }

    // 删除订单
    @PostMapping("/delete")
    public ResponseEntity<String> deleteOrder(@RequestParam("orderId") Long orderId) {

        boolean isDeleted = orderService.deleteOrder(orderId);
        if (isDeleted) {
            return ResponseEntity.ok("订单已删除");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addall")
    public ResponseEntity<String> addOrder(@RequestBody OrderRequest orderRequest) {
        try {
            // 从请求中提取订单和产品数据
            Order order = orderRequest.getOrder();
            List<OrderDetail> orderDetails = orderRequest.getOrderDetails();

            // 调用服务层方法
            orderService.createOrderWithDetails(order, orderDetails);

            return ResponseEntity.ok("订单创建成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("订单创建失败: " + e.getMessage());
        }
    }
}

