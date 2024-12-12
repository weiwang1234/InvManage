package com.happycode.Controller;

import com.happycode.model.OrderDetail;
import com.happycode.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordersdetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    // 获取所有订单明细
    @PostMapping("/getAll")
    public List<OrderDetail> getAllOrderDetails(@RequestBody(required = false) Object body) {
        return orderDetailService.getAllOrderDetails();
    }

    // 根据订单编号获取订单明细
    @PostMapping("/getByOrderId")
    public List<OrderDetail> getOrderDetailsByOrderId(@RequestBody OrderDetail orderdetail) {
       System.out.println( orderdetail.toString());
        System.out.println( orderDetailService.getOrderDetailsByOrderId(orderdetail.getOrderid()));

        return orderDetailService.getOrderDetailsByOrderId(orderdetail.getOrderid());
    }

    // 根据订单明细ID获取订单明细
    @PostMapping("/getById")
    public Optional<OrderDetail> getOrderDetailById(@RequestBody OrderDetail orderdetail) {
        return orderDetailService.getOrderDetailById(orderdetail.getOrdetailid());
    }

    // 新增订单明细
    @PostMapping("/create")
    public OrderDetail createOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.createOrderDetail(orderDetail);
    }

    // 更新订单明细
    @PostMapping("/update")
    public OrderDetail updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.updateOrderDetail(orderDetail.getOrdetailid(), orderDetail);
    }

    // 删除订单明细
    @PostMapping("/delete")
    public void deleteOrderDetail(@RequestBody OrderDetail orderDetail) {

        orderDetailService.deleteOrderDetail(orderDetail.getOrdetailid());
    }
}
