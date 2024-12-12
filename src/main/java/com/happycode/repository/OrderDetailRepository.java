package com.happycode.repository;

import com.happycode.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // 可以根据订单编号查询订单明细
    void deleteByOrderid(Long orderid);

    List<OrderDetail> findByOrderid(Long orderId);
}
