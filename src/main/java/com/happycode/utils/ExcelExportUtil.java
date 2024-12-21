package com.happycode.export.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 通用 Excel 导出工具类
 */
public class ExcelExportUtil {

    /**
     * 导出 Excel 文件
     *
     * @param sheetName 表格名称
     * @param headers   表头
     * @param data      数据
     * @return ResponseEntity<byte[]>，包含生成的 Excel 文件
     */
    public static ResponseEntity<byte[]> exportToExcel(String sheetName, List<String> headers, JSONArray data) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // 创建表头
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                headerRow.createCell(i).setCellValue(headers.get(i));
            }

            // 填充数据
            for (int i = 0; i < data.size(); i++) {
                JSONObject rowData = data.getJSONObject(i);
                Row row = sheet.createRow(i + 1);
                int cellIndex = 0;
                for (String key : rowData.keySet()) {
                    row.createCell(cellIndex++).setCellValue(rowData.getString(key));
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            HttpHeaders headersHttp = new HttpHeaders();
            headersHttp.add("Content-Disposition", "attachment; filename=" + sheetName + ".xlsx");

            return new ResponseEntity<>(outputStream.toByteArray(), headersHttp, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
