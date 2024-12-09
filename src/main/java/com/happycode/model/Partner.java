package com.happycode.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "partner_list") // 映射到数据库中的partner_list表
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partnerid;

    private String partnername;
    private String partneraddress;
    private String partnerphone;
    private String partnertype;
    private String partnerstatus;
}
