package com.happycode.model.home;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Maintenance {
    private Long orderid; //订单id
    private String orderparname;//客户名称
    private String orderdate; //上次保养日期
    private String partnerphone;

    public Maintenance(Long orderid, String orderparname, String orderdate, String partnerphone) {
        this.orderid = orderid;
        this.orderparname = orderparname;
        this.orderdate = orderdate;
        this.partnerphone = partnerphone;
    }
}