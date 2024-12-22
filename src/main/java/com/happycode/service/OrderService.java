package com.happycode.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.happycode.model.Inventory;
import com.happycode.model.Order;
import com.happycode.model.OrderDetail;
import com.happycode.model.SearchCriteria;
import com.happycode.repository.InventoryRepository;
import com.happycode.repository.OrderDetailRepository;
import com.happycode.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderdetailrepository;
    @Autowired
    private InventoryRepository inventoryRepository;

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
        // 查询订单明细
        List<OrderDetail> orderDetails = orderdetailrepository.findByOrderid(orderId);

        // 恢复库存
        for (OrderDetail detail : orderDetails) {
            long productId = detail.getProductid();
            int quantitySold = detail.getQuantity();

            // 查找库存记录
            Inventory inventory = inventoryRepository.findByProductid(productId);
            if (inventory != null) {
                // 恢复库存数量
                inventory.setQuantity(inventory.getQuantity() + quantitySold);
                inventoryRepository.save(inventory);
            } else {
                throw new RuntimeException("库存记录不存在，无法恢复库存！");
            }
        }

        // 删除订单明细
        orderdetailrepository.deleteByOrderid(orderId);

        // 删除主订单
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }

        return false;
    }
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
    @Transactional
    public void createOrderWithDetails(Order order, List<OrderDetail> orderDetails) {
        // 遍历订单详情，检查库存是否充足
        for (OrderDetail detail : orderDetails) {
            long productId = detail.getProductid();
            int quantitySold = detail.getQuantity();

            // 获取当前库存
            Inventory inventory = inventoryRepository.findByProductid(productId);

            if (inventory == null || inventory.getQuantity() < quantitySold) {
                throw new RuntimeException("产品 " + detail.getProductname() + " 的库存不足，无法完成订单！");
            }
        }

        // 更新库存
        for (OrderDetail detail : orderDetails) {
            long productId = detail.getProductid();
            int quantitySold = detail.getQuantity();

            Inventory inventory = inventoryRepository.findByProductid(productId);
            inventory.setQuantity(inventory.getQuantity() - quantitySold);
            inventoryRepository.save(inventory);
        }

        // 保存订单，返回生成的主键
        Order savedOrder = orderRepository.save(order);

        // 为每个产品设置订单ID
        orderDetails.forEach(detail -> detail.setOrderid(savedOrder.getOrderid()));

        // 批量保存产品
        orderdetailrepository.saveAll(orderDetails);
    }

    //只查询当天的数据
    @Transactional
    public List<Order> findByOrderDateOrInsertTime() {
        String today = LocalDate.now().toString(); // 转换为 yyyy-MM-dd 格式
        return orderRepository.findByOrderDateOrInsertTime(today);

    }

    public List<Order> searchOrders(SearchCriteria criteria) {
        return orderRepository.findOrders(criteria.getStartDate(), criteria.getEndDate(), criteria.getCustomerName());
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
                sqlwhere+="  and orderparname = '"+jsonObject.getString("orderparname")+"'";

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
