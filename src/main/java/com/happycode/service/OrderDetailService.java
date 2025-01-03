package com.happycode.service;

import com.happycode.model.Inventory;
import com.happycode.model.MonthEnd.OderDetailSummary;
import com.happycode.model.Order;
import com.happycode.model.OrderDetail;
import com.happycode.model.home.SalesStatistics;
import com.happycode.repository.InventoryRepository;
import com.happycode.repository.OrderDetailRepository;
import com.happycode.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private OrderService orderservice;
    @Autowired
    private OrderRepository orderrepository;




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

        // 更新总订单额度
        long orderid  = orderDetail.getOrderid();
        //判断订单下是否只有一笔明细，是则删除总订单，否则更新总订单的金额
        List<OrderDetail>  orderdetail = getOrderDetailsByOrderId(orderid);
        if (orderdetail.size()>1){
            Order order = orderrepository.findById(orderid)
                    .orElseThrow(() -> new RuntimeException("订单不存在！"));
            order.setOrdertotalamount(order.getOrdertotalamount()-orderDetail.getUnitprice().doubleValue());
            orderrepository.save(order);
            double quantity = orderDetail.getQuantity();

            // 查找库存记录
            Inventory inventory = inventoryRepository.findByProductid(orderDetail.getProductid());
            if (inventory != null) {
                // 恢复库存数量
                inventory.setQuantity(inventory.getQuantity() + quantity);
                inventoryRepository.save(inventory);
            } else {
                throw new RuntimeException("库存记录不存在，无法恢复库存！");
            }

            // 删除订单明细
            orderDetailRepository.deleteById(ordetailid);

        }else{
            orderservice.deleteOrder(orderid);
        }
    }


    public void deleteOrderidOrderDetail(Long orderid) {
        orderDetailRepository.deleteById(orderid);
    }
    public List<OderDetailSummary> getMonthlyOrderSummary(String date) {
        return orderDetailRepository.getMonthlyOrderSummary(date);
    }

    public List<OrderDetail> findByOrderDate(String orderDate) {
        return orderDetailRepository.findByOrderDate(orderDate);
    }

    public BigDecimal findTotalSalesByOrderDate(String orderDate) {
        return orderDetailRepository.findTotalSalesByOrderDate(orderDate);
    }
    public BigDecimal getSalesStatistics(String orderDate) {

        System.out.println(orderDetailRepository.getSalesStatistics(orderDate));
        return orderDetailRepository.getSalesStatistics(orderDate);
    }

    public List<SalesStatistics> getSalesStatisticsByMonth(String orderDate) {
        return orderDetailRepository.getSalesStatisticsByMonth(orderDate);
    }

}
