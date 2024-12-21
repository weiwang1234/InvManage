package com.happycode.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.happycode.model.PurchaseOrderSummaryExprot;
import com.happycode.repository.PurchaseOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ExportsService {
    @Autowired
    private PurchaseOrderDetailRepository repository;

    public List<PurchaseOrderSummaryExprot>  PurchaseOrderDetailExport(@RequestBody JSONObject request) {

        return  repository.findOrderDetailsExprot(
                request.getString("startDate"),
                request.getString("endDate"),
                request.getString("customerName")
        );





    }


    }
