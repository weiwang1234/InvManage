package com.happycode.utils;

public class InsufficientStockException extends RuntimeException {

    // 构造方法，传入异常消息
    public InsufficientStockException(String message) {
        super(message);
    }

    // 可选：也可以添加其他构造方法，如传递错误码等
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
