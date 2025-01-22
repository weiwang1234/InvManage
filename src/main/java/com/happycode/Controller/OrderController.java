package com.happycode.Controller;

import com.happycode.model.*;
import com.happycode.model.home.Maintenance;
import com.happycode.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import  com.happycode.model.home.salesData;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.happycode.service.HomeService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private HomeService HomeService;



    // 获取所有订单
    @PostMapping("/getAll")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // 获取当日订单
    @PostMapping("/getsamday")
    public List<Order> getsamdayOrders() {

        return orderService.findByOrderDateOrInsertTime();
    }
    @PostMapping("/searchOrders")
    public List<Order> searchOrders(@RequestBody SearchCriteria criteria) {
        return orderService.searchOrders(criteria);
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
    @PostMapping("/find")
    public List<Maintenance> findOrders(
            @RequestParam String targetDate,
            @RequestParam int daysDifference) {
        return orderService.findOrdersByMaintenanceReminderAndDateDifference(targetDate, daysDifference);
    }

    @PostMapping("/updatereminder")
    public Order updatereminder(
            @RequestParam Long orderid) {
        return orderService.updatereminder(orderid);
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
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("订单创建失败: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("订单创建失败: " + e.getMessage());
        }
    }

    @PostMapping("/summary")
    public ResponseEntity<?> getOrderSummary(@RequestBody Map<String, Object> params) {
        // 默认查询条件为 null，调用服务层动态查询
        System.out.println("接收到的请求体：" + params); // 打印接收到的请求内容

        String sWhere = (String) params.get("sWhere");


        return ResponseEntity.ok(orderService.getOrderSummary(sWhere)); // 确保返回 200 状态码

    }
    @PostMapping("/summarydetails")
    public ResponseEntity<?> getSummaryDetails(@RequestBody Map<String, Object> params) {
        // 默认查询条件为 null，调用服务层动态查询
        System.out.println("接收到的请求体：" + params); // 打印接收到的请求内容

        String sWhere = (String) params.get("sWhere");


        return ResponseEntity.ok(orderService.getOrderSummary(sWhere)); // 确保返回 200 状态码

    }

    @PostMapping("/summarydetailsviews")
    public ResponseEntity<?> getSummarydetailsViews(@RequestBody Map<String, Object> params) {
        // 默认查询条件为 null，调用服务层动态查询
        System.out.println("接收到的请求体：" + params); // 打印接收到的请求内容

        String sWhere = (String) params.get("sWhere");


        return ResponseEntity.ok(orderService.summarydetailsViews(sWhere)); // 确保返回 200 状态码

    }

    @PostMapping("/getHomepageSum")
    public ResponseEntity<salesData>  getHomepageSum(@RequestBody OrderRequest orderRequest) {
        // 默认查询条件为 null，调用服务层动态查询
        return ResponseEntity.ok( HomeService.getHomepageSum(""));// 确保返回 200 状态码

    }




}

