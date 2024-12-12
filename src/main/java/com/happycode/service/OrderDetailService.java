package com.happycode.service;

import com.happycode.model.OrderDetail;
import com.happycode.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public List<OrderDetail> getOrderDetailsByOrderId(long orderId) {
        return orderDetailRepository.findByOrderid(orderId);
    }

    public Optional<OrderDetail> getOrderDetailById(Long ordetailid) {
        return orderDetailRepository.findById(ordetailid);
    }

    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail updateOrderDetail(Long ordetailid, OrderDetail orderDetail) {
        orderDetail.setOrdetailid(ordetailid);
        return orderDetailRepository.save(orderDetail);
    }

    public void deleteOrderDetail(Long ordetailid) {
        orderDetailRepository.deleteById(ordetailid);
    }

    public void deleteOrderidOrderDetail(Long orderid) {
        orderDetailRepository.deleteById(orderid);
    }

}
