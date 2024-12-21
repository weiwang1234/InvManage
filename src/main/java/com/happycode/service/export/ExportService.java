package com.happycode.service.export;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.util.List;

/**
 * 通用导出服务接口
 */
public interface ExportService {

    /**
     * 获取导出数据
     *
     * @param request 请求参数
     * @return ExportData 导出数据
     */
    ExportData getExportData(JSONObject request);

    /**
     * 封装导出数据的类
     */
    @Getter
    class ExportData {
        private final String sheetName;
        private final List<String> headers;
        private final JSONArray data;

        public ExportData(String sheetName, List<String> headers, JSONArray data) {
            this.sheetName = sheetName;
            this.headers = headers;
            this.data = data;
        }

    }
}
