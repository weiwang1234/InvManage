package com.happycode.service;

import com.happycode.model.Order;
import com.happycode.model.OrderDetail;
import com.happycode.repository.OrderDetailRepository;
import com.happycode.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderdetailrepository;


    // 获取所有订单
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 根据订单ID获取订单
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // 添加新订单
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    // 更新订单
    public Order updateOrder(Long orderId, Order orderDetails) {
        if (orderRepository.existsById(orderId)) {
            orderDetails.setOrderid(orderId);
            return orderRepository.save(orderDetails);
        }
        return null;
    }

    // 删除订单
    @Transactional
    public boolean deleteOrder(Long orderId) {
        orderdetailrepository.deleteByOrderid(orderId);

        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);

            return true;
        }
        return false;
    }

    @Transactional
    public void createOrderWithDetails(Order order, List<OrderDetail> orderDetails) {
        // 保存订单，返回生成的主键
        Order savedOrder = orderRepository.save(order);

        // 为每个产品设置订单ID
        orderDetails.forEach(detail -> detail.setOrderid(savedOrder.getOrderid()));

        // 批量保存产品
        orderdetailrepository.saveAll(orderDetails);
    }
}
