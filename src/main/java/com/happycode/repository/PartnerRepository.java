package com.happycode.repository;

import com.happycode.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    // 可以根据需要添加查询方法，比如通过名称或电话查询
    List<Partner> findByPartnerstatus(String partnerstatus);
}
