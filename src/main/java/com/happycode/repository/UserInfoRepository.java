package com.happycode.repository;

import com.happycode.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    // 可以根据需要添加自定义查询方法
}
