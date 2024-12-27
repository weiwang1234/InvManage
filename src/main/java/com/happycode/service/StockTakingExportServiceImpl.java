package com.happycode.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.happycode.annotation.ExportType;
import com.happycode.model.MonthEndStockDetail;
import com.happycode.model.PurchaseOrderSummaryExprot;
import com.happycode.service.export.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@ExportType("StockTaking")
public class StockTakingExportServiceImpl implements ExportService {
    @Autowired
    MonthEndStockDetailService  monthendstockdetailservice;
    @Override
    public ExportData getExportData(JSONObject request) {
        // 获取服务返回的原始数据
        List<MonthEndStockDetail> rawData = monthendstockdetailservice.getAllByStockMonth(request.getString("stockmonth"));

        // 指定字段的固定顺序
        List<String> fieldOrder = Arrays.asList("stockmonth", "productname",
                "lastmonthinventory", "monthpurchases","monthpurchasesamount",
                "monthprocessing","monthprocessedoutput","monthsoldquantity","monthsoldamount","monthinventory");

        // 创建有序的 JSONArray
        JSONArray orderedArray = new JSONArray();
        for (MonthEndStockDetail item : rawData) {
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
        List<String> headers = Arrays.asList("盘点月份","产品名称","上月库存数量","本月进货数量","本月进货金额","加工数量","本月加工产出数量",
                "本月卖出数量", "本月卖出金额", "本月库存");

        return new ExportData("订单数据", headers, orderedArray);
    }
}