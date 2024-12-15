package com.happycode.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.happycode.model.Order;
import com.happycode.model.OrderDetail;
import com.happycode.repository.OrderDetailRepository;
import com.happycode.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderdetailrepository;
    @PersistenceContext
    private EntityManager entityManager;


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

    public JSONArray getOrderSummary(String sWhere) {

        JSONObject jsonObject = JSON.parseObject(sWhere);
        String sqlwhere  ="SELECT orderparid,orderparname, SUM(ordertotalamount) AS totalAmount "
                 + " FROM orders_list WHERE 1=1 ";
        if (jsonObject != null && !jsonObject.isEmpty()) {
            if (!jsonObject.getString("StartDate").isEmpty()&&
            !jsonObject.getString("EndDate").isEmpty()) {
                sqlwhere += " and  orderdate>='"+jsonObject.getString("StartDate")+"' AND orderdate<='"+jsonObject.getString("EndDate")+"'";
                if (!jsonObject.getString("orderparname").isEmpty()) {
                    sqlwhere+=" and  orderparname = '"+jsonObject.getString("orderparname")+"'";

                }
            } else if (!jsonObject.getString("orderparname").isEmpty()) {
                sqlwhere+="   orderparname = '"+jsonObject.getString("orderparname")+"'";

                }
            }

        sqlwhere += " GROUP BY orderparid, orderparname ORDER BY totalAmount  desc ";

        Query query = entityManager.createNativeQuery(sqlwhere);

        List<Object[]> results = query.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (Object[] row : results) {
            JSONObject json = new JSONObject();
            json.put("orderparid", row[0]);
            json.put("orderparname", row[1]);
            json.put("totalAmount", row[2]);
            jsonArray.add(json);
        }


        return jsonArray;

    }

    public JSONArray summarydetails(String sWhere) {

        JSONObject jsonObject = JSON.parseObject(sWhere);
        String sqlwhere  =" select orderparname ,productname ,sum(quantity) as quantity ,sum(unitprice) as  unitprice from orders_detail od where   ";
        if (jsonObject != null && !jsonObject.isEmpty()) {
            if (!jsonObject.getString("StartDate").isEmpty()&&
                    !jsonObject.getString("EndDate").isEmpty()) {
                sqlwhere += "  1=1 and orderdate>='"+jsonObject.getString("StartDate")+"' AND orderdate<='"+jsonObject.getString("EndDate")+"'";
                if (!jsonObject.getString("orderparname").isEmpty()) {
                    sqlwhere+=" and  orderparname = '"+jsonObject.getString("orderparname")+"'";

                }
            } else if (!jsonObject.getString("orderparname").isEmpty()) {
                sqlwhere+=" 1=1 and orderparname = '"+jsonObject.getString("orderparname")+"'";

            }else {
                sqlwhere  =" select * from orders_detail od where  1=2 ";
            }
            sqlwhere += " GROUP BY orderparid,productid,orderparname,productname ORDER BY unitprice  desc ";

        }else {
            sqlwhere  =" select * from orders_detail od where  1=2 ";
        }


        Query query = entityManager.createNativeQuery(sqlwhere);

        List<Object[]> results = query.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (Object[] row : results) {
            JSONObject json = new JSONObject();
            json.put("orderparname", row[0]);
            json.put("productname", row[1]);
            json.put("quantity", row[2]);
            json.put("unitprice", row[3]);
            jsonArray.add(json);
        }


        return jsonArray;

    }

    public JSONArray summarydetailsViews(String sWhere) {

        JSONObject jsonObject = JSON.parseObject(sWhere);
        String sqlwhere  =" select productname ,sum(quantity) as quantity ,sum(unitprice) as  unitprice from orders_detail od where    ";
        if (jsonObject != null && !jsonObject.isEmpty()) {
            if (!jsonObject.getString("StartDate").isEmpty()&&
                    !jsonObject.getString("EndDate").isEmpty()) {
                sqlwhere += "  1=1 and orderdate>='"+jsonObject.getString("StartDate")+"' AND orderdate<='"+jsonObject.getString("EndDate")+"'";
                if (!jsonObject.getString("orderparid").isEmpty()) {
                    sqlwhere+=" and  orderparid = '"+jsonObject.getString("orderparid")+"'";

                }
            } else if (!jsonObject.getString("orderparid").isEmpty()) {
                sqlwhere+=" 1=1 and orderparid = '"+jsonObject.getString("orderparid")+"'";

            }else {
                sqlwhere  =" select * from orders_detail od where  1=2 ";
            }
            sqlwhere += " GROUP BY orderparid,productid,productname ORDER BY unitprice  desc ";

        }else {
            sqlwhere  =" select * from orders_detail od where  1=2 ";
        }


        Query query = entityManager.createNativeQuery(sqlwhere);

        List<Object[]> results = query.getResultList();
        JSONArray jsonArray = new JSONArray();
        for (Object[] row : results) {
            JSONObject json = new JSONObject();
            json.put("productname", row[0]);
            json.put("quantity", row[1]);
            json.put("unitprice", row[2]);
            jsonArray.add(json);
        }


        return jsonArray;

    }
}
