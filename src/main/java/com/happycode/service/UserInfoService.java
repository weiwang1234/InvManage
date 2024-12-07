package com.happycode.service;

import com.happycode.model.UserInfo;
import com.happycode.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

    public UserInfo getUserById(Long id) {
        return userInfoRepository.findById(id).orElse(null);
    }

    // 你还可以根据用户名或者其他字段自定义查询方法
    // public List<UserInfo> findByUsername(String username);
}
