package com.happycode.service.export;

import com.happycode.annotation.ExportType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理所有导出服务的管理器
 */
@Component
public class ExportServiceManager {

    private final Map<String, ExportService> exportServiceMapping = new HashMap<>();

    public ExportServiceManager(List<ExportService> exportServices) {
        for (ExportService service : exportServices) {
            ExportType annotation = service.getClass().getAnnotation(ExportType.class);
            if (annotation != null) {
                exportServiceMapping.put(annotation.value(), service);
            }
        }
    }

    public ExportService getExportService(String exportType) {
        return exportServiceMapping.get(exportType);
    }
}
