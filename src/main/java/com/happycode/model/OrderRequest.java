package com.happycode.model;

import lombok.Data;

import java.util.List;
@Data
public class OrderRequest {
    private Order order;
    private List<OrderDetail> orderDetails;

    // Getters and Setters
}
