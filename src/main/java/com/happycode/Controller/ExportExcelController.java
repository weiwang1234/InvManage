package com.happycode.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.happycode.service.OrderService;
import com.happycode.service.export.ExportService;
import com.happycode.service.export.ExportServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/exports")
public class ExportExcelController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ExportServiceManager exportServiceManager;

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

    //通用导出方法
    @PostMapping("/{exportType}")
    public ResponseEntity<byte[]> exportData(@PathVariable("exportType") String exportType, @RequestBody JSONObject request) {
        try {
            // 根据 exportType 获取对应的导出服务
            ExportService exportService = exportServiceManager.getExportService(exportType);
            if (exportService == null) {
                return ResponseEntity.badRequest().body(null);
            }

            // 获取导出数据
            ExportService.ExportData exportData = exportService.getExportData(request);

            // 调用工具类生成 Excel
            return com.happycode.export.util.ExcelExportUtil.exportToExcel(exportData.getSheetName(), exportData.getHeaders(), exportData.getData());
        } catch (Exception e) {
            log.error("导出报表失败{}{}", e.getMessage(), exportType, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    }



