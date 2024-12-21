package com.happycode.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.happycode.annotation.ExportType;
import com.happycode.model.PurchaseOrderSummaryExprot;
import com.happycode.service.export.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Slf4j
@Service
@ExportType("orders")
public class OrdersExportServiceImpl implements ExportService {
    @Autowired
    private ExportsService exportsservice;
    @Override
    public ExportData getExportData(JSONObject request) {
        // 获取服务返回的原始数据
        List<PurchaseOrderSummaryExprot> rawData = exportsservice.PurchaseOrderDetailExport(request);

        // 指定字段的固定顺序
        List<String> fieldOrder = Arrays.asList("orderparname", "productname", "quantity", "unitprice");

        // 创建有序的 JSONArray
        JSONArray orderedArray = new JSONArray();
        for (PurchaseOrderSummaryExprot item : rawData) {
            // 使用有序 JSONObject（LinkedHashMap）
            JSONObject orderedObject = new JSONObject(true);
            for (String key : fieldOrder) {
                Object value = JSON.parseObject(JSON.toJSONString(item)).get(key);
                orderedObject.put(key, value);
            }
            orderedArray.add(orderedObject);
        }

        // 打印日志验证顺序
        log.info("Ordered JSONArray:\n" + JSON.toJSONString(orderedArray, SerializerFeature.PrettyFormat));

        // 表头
        List<String> headers = Arrays.asList("客户名称", "产品名称", "数量", "金额");

        return new ExportData("订单数据", headers, orderedArray);
    }
}