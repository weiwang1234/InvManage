package com.happycode.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.happycode.service.OrderService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/exports")
public class ExportExcelController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/exportordersquery")
    public ResponseEntity<byte[]> exportOrdersToExcel(@RequestBody JSONObject request) {
        try {
            // 模拟从数据库或其他来源获取的订单数据（JSONArray）
            JSONArray orders =orderService.summarydetails(request.toJSONString());

            // 创建 Excel 文件
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("订单数据");

            // 设置表头
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("客户名称");
            headerRow.createCell(1).setCellValue("产品名称");
            headerRow.createCell(2).setCellValue("产品汇总数量");
            headerRow.createCell(3).setCellValue("产品汇总金额");

            // 填充数据
            for (int i = 0; i < orders.size(); i++) {
                JSONObject order = orders.getJSONObject(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(order.getString("orderparname"));
                row.createCell(1).setCellValue(order.getString("productname"));
                row.createCell(2).setCellValue(order.getString("quantity"));
                row.createCell(3).setCellValue(order.getString("unitprice"));
            }

            // 将 Excel 写入字节流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            // 设置响应头，返回 Excel 文件
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=订单数据导出.xlsx");
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private JSONArray getOrders(JSONObject request) {
        // 模拟生成 JSON 数据
        JSONArray orders = new JSONArray();

        JSONObject order1 = new JSONObject();
        order1.put("orderId", "1");
        order1.put("customerName", "客户A");
        order1.put("totalAmount", 123.45);

        JSONObject order2 = new JSONObject();
        order2.put("orderId", "2");
        order2.put("customerName", "客户B");
        order2.put("totalAmount", 678.90);

        orders.add(order1);
        orders.add(order2);

        return orders;
    }
}

