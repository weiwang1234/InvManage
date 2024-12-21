package com.happycode.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义注解，用于标记 ExportService 的导出类型
 */
@Retention(RetentionPolicy.RUNTIME) // 运行时可通过反射获取
public @interface ExportType {
    String value(); // 导出类型的标识
}
