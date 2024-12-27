package com.happycode.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.happycode.model.*;
import com.happycode.service.*;
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
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/exports")
public class ExportExcelController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ExportServiceManager exportServiceManager;
    @Autowired
    private OrderDetailService OrderDetailService;
    @Autowired
    private PurchaseOrderDetailService purchaseorderdetailservice;
    @Autowired
    private ProductProcessingDetailService productprocessingdetailservice;
    @Autowired
    private ProductProcessingService productprocessingservice;
    @Autowired
    private MonthEndStockDetailService monthendstockdetailservice;
    @Autowired
    private ProfitStatementService profitstatementservice;
    @Autowired
    private OtherExpensesService otherexpensesservice;





    @PostMapping("/exportordersquery")
    public ResponseEntity<byte[]> exportOrdersToExcel(@RequestBody JSONObject request) {
        try {
            // 模拟从数据库或其他来源获取的订单数据（JSONArray）
            JSONArray orders =orderService.summarydetails(request.toJSONString());

            // 创建 Excel 文件
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("卖出订单数据");

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

    @PostMapping("/ProfitStatement")
    public ResponseEntity<byte[]> exportProfitStatement(@RequestParam String profitmonth) throws IOException {

        //卖出明细
         List<OrderDetail> orderdetail = OrderDetailService.findByOrderDate(profitmonth);
        //进货明细
        List<PurchaseOrderDetail> purchaseorderdetail = purchaseorderdetailservice.findByOrderDate(profitmonth);
//        List<ProductProcessingDetail> productprocessingdetails =productprocessingdetailservice.findByProductProcessingDetailDate(profitmonth);
//        List<ProductProcessing> productprocessings =productprocessingservice.findByProductProcessingDate(profitmonth);
        List<MonthEndStockDetail> monthendstockdetails =monthendstockdetailservice.getAllByStockMonth(profitmonth.substring(0, profitmonth.length() - 1));

        List<OtherExpenses> otherexpensesservices =   otherexpensesservice.findByOtherExpensesDate(profitmonth);


        //盈利表
        Optional<ProfitStatement> ProfitStatements = profitstatementservice.getProfitStatementByMonth(profitmonth.substring(0, profitmonth.length() - 1));

        XSSFWorkbook workbook = new XSSFWorkbook();

        // 创建第一个 sheet
        Sheet sheet1 = workbook.createSheet("进货明细");

        // 填充第一个 sheet 数据
        Row headerRow1 = sheet1.createRow(0);
        headerRow1.createCell(0).setCellValue("日期");
        headerRow1.createCell(1).setCellValue("进货来源方");
        headerRow1.createCell(2).setCellValue("产品名称");
        headerRow1.createCell(3).setCellValue("产品数量");
        headerRow1.createCell(4).setCellValue("产品金额");

        int rowNum = 1;
        for (PurchaseOrderDetail PurchaseOrderDetail : purchaseorderdetail) {
            Row row = sheet1.createRow(rowNum++);
            row.createCell(0).setCellValue(PurchaseOrderDetail.getOrderdate());
            row.createCell(1).setCellValue(PurchaseOrderDetail.getOrderparname());
            row.createCell(2).setCellValue(PurchaseOrderDetail.getProductname());
            row.createCell(3).setCellValue(PurchaseOrderDetail.getQuantity());
            row.createCell(4).setCellValue(PurchaseOrderDetail.getUnitprice().doubleValue());
        }

        // 创建第二个 sheet
        Sheet sheet2 = workbook.createSheet("卖货明细");

        // 填充第二个 sheet 数据
        Row headerRow2 = sheet2.createRow(0);
        headerRow2.createCell(0).setCellValue("日期");
        headerRow2.createCell(1).setCellValue("客户名称");
        headerRow2.createCell(2).setCellValue("产品数量");
        headerRow2.createCell(3).setCellValue("产品金额");


         rowNum = 1;
        for (OrderDetail orderdetailm : orderdetail) {
            Row row = sheet2.createRow(rowNum++);
            row.createCell(0).setCellValue(orderdetailm.getOrderdate());
            row.createCell(1).setCellValue(orderdetailm.getOrderparname());
            row.createCell(2).setCellValue(orderdetailm.getQuantity());
            row.createCell(3).setCellValue(orderdetailm.getUnitprice().doubleValue());

        }

//        // 创建第三个 sheet
//        Sheet sheet3 = workbook.createSheet("加工明细");
//
//        // 填充第三个 sheet 数据
//        Row headerRow3 = sheet3.createRow(0);
//        headerRow3.createCell(0).setCellValue("日期");
//        headerRow3.createCell(1).setCellValue("产品名称");
//        headerRow3.createCell(2).setCellValue("加工数量");
//
//
//        rowNum = 1;
//        for (ProductProcessing productprocessing : productprocessings) {
//            Row row = sheet3.createRow(rowNum++);
//            row.createCell(0).setCellValue(productprocessing.getProcessingdate());
//            row.createCell(1).setCellValue(productprocessing.getProductname());
//            row.createCell(2).setCellValue(productprocessing.getQuantity());
//        }
//
//
//        // 创建第四个 sheet
//        Sheet sheet4 = workbook.createSheet("加工产出明细");
//
//        // 填充第四个 sheet 数据
//        Row headerRow4 = sheet4.createRow(0);
//        headerRow4.createCell(0).setCellValue("日期");
//        headerRow4.createCell(1).setCellValue("产出产品名称");
//        headerRow4.createCell(2).setCellValue("产出产品数据量");
//
//
//        rowNum = 1;
//        for (ProductProcessingDetail productprocessingdetail : productprocessingdetails) {
//            Row row = sheet4.createRow(rowNum++);
//            row.createCell(0).setCellValue(productprocessingdetail.getProcessingdetaildate());
//            row.createCell(1).setCellValue(productprocessingdetail.getOutputproductname());
//            row.createCell(2).setCellValue(productprocessingdetail.getOutputcount());
//        }


        // 创建第五个 sheet
        Sheet sheet5 = workbook.createSheet("产品月底明细");

        // 填充第四个 sheet 数据
        Row headerRow5 = sheet5.createRow(0);
        headerRow5.createCell(0).setCellValue("月份");
        headerRow5.createCell(1).setCellValue("产品名称");
        headerRow5.createCell(2).setCellValue("上月库存数量");
        headerRow5.createCell(3).setCellValue("本月进货数量");
        headerRow5.createCell(4).setCellValue("本月进货金额");
        headerRow5.createCell(5).setCellValue("本月加工数量");
        headerRow5.createCell(6).setCellValue("本月加工产出数量");
        headerRow5.createCell(7).setCellValue("本月卖出数量");
        headerRow5.createCell(8).setCellValue("本月卖出金额");
        rowNum = 1;
        for (MonthEndStockDetail monthendstockdetail : monthendstockdetails) {
            Row row = sheet5.createRow(rowNum++);
            row.createCell(0).setCellValue(monthendstockdetail.getStockmonth());
            row.createCell(1).setCellValue(monthendstockdetail.getProductname());
            row.createCell(2).setCellValue(monthendstockdetail.getLastmonthinventory());
            row.createCell(3).setCellValue(monthendstockdetail.getMonthpurchases());
            row.createCell(4).setCellValue(monthendstockdetail.getMonthpurchasesamount().doubleValue());
            row.createCell(5).setCellValue(monthendstockdetail.getMonthprocessing());
            row.createCell(6).setCellValue(monthendstockdetail.getMonthprocessedoutput());
            row.createCell(7).setCellValue(monthendstockdetail.getMonthsoldquantity());
            row.createCell(8).setCellValue(monthendstockdetail.getMonthsoldamount().doubleValue());

        }


        Sheet sheet6 = workbook.createSheet("其他支出明细");

        // 填充第四个 sheet 数据
        Row headerRow6 = sheet6.createRow(0);
        headerRow6.createCell(0).setCellValue("月份");
        headerRow6.createCell(1).setCellValue("支出名称");
        headerRow6.createCell(2).setCellValue("支出备注");
        headerRow6.createCell(3).setCellValue("支出金额");
        rowNum = 1;
        for (OtherExpenses OtherExpenses : otherexpensesservices) {
            Row row = sheet6.createRow(rowNum++);
            row.createCell(0).setCellValue(OtherExpenses.getOtherexpensesdate());
            row.createCell(1).setCellValue(OtherExpenses.getOtherexpensesname());
            row.createCell(2).setCellValue(OtherExpenses.getOtherexpensesremak());
            row.createCell(3).setCellValue(OtherExpenses.getOtherexpensesamount().doubleValue());


        }

        Sheet sheet7 = workbook.createSheet("盈利表");

        // 填充第四个 sheet 数据
        Row headerRow7= sheet7.createRow(0);
        headerRow7.createCell(0).setCellValue("月份");
        headerRow7.createCell(1).setCellValue("进货支出");
        headerRow7.createCell(2).setCellValue("其他支出");
        headerRow7.createCell(3).setCellValue("收入");
        headerRow7.createCell(4).setCellValue("净利润");

        rowNum = 1;

        Row row = sheet7.createRow(rowNum++);
        row.createCell(0).setCellValue(ProfitStatements.get().getProfitmonth());
        row.createCell(1).setCellValue(ProfitStatements.get().getPurchasingexpenses().doubleValue());
        row.createCell(2).setCellValue(ProfitStatements.get().getOtherexpenses().doubleValue());
        row.createCell(3).setCellValue(ProfitStatements.get().getSalesrevenue().doubleValue());
        row.createCell(4).setCellValue(ProfitStatements.get().getNetprofit().doubleValue());






        // 将 Excel 写入字节数组输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        byte[] fileContent = bos.toByteArray();

        // 返回带有多个 sheet 的 Excel 文件
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=profit_statement_" + profitmonth + ".xlsx")
                .body(fileContent);
    }


}



