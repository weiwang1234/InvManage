package com.happycode.service;

import com.happycode.model.Inventory;
import com.happycode.model.OrderDetail;
import com.happycode.repository.InventoryRepository;
import com.happycode.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

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

    @Transactional
    public void deleteOrderDetail(Long ordetailid) {
        // 获取订单明细
        OrderDetail orderDetail = orderDetailRepository.findById(ordetailid)
                .orElseThrow(() -> new RuntimeException("订单明细不存在，无法删除！"));

        // 恢复库存
        long productId = orderDetail.getProductid();
        int quantity = orderDetail.getQuantity();

        // 查找库存记录
        Inventory inventory = inventoryRepository.findByProductid(productId);
        if (inventory != null) {
            // 恢复库存数量
            inventory.setQuantity(inventory.getQuantity() + quantity);
            inventoryRepository.save(inventory);
        } else {
            throw new RuntimeException("库存记录不存在，无法恢复库存！");
        }

        // 删除订单明细
        orderDetailRepository.deleteById(ordetailid);
    }


    public void deleteOrderidOrderDetail(Long orderid) {
        orderDetailRepository.deleteById(orderid);
    }

}
