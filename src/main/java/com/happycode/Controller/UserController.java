package com.happycode.Controller;

import com.happycode.model.UserInfo;
import com.happycode.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    // 查询所有用户
    @PostMapping("/getAll")
    public List<UserInfo> getAllUsers(@RequestBody(required = false) Object params) {
        // 如果需要参数传递，可以从 params 中提取（例如分页、过滤条件等）
        return userInfoRepository.findAll();
    }

    // 根据用户ID查询单个用户
    @PostMapping("/getById")
    public UserInfo getUserById(@RequestBody UserInfo userInfo) {
        return userInfoRepository.findById(userInfo.getUserid()).orElse(null);
    }

    // 添加用户
    @PostMapping("/add")
    public UserInfo createUser(@RequestBody UserInfo userInfo) {
        return userInfoRepository.save(userInfo);  // 将用户信息保存到数据库
    }

    // 更新用户
    @PostMapping("/update")
    public UserInfo updateUser(@RequestBody UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    // 删除用户
    @PostMapping("/delete")
    public void deleteUser(@RequestBody UserInfo userInfo) {
        userInfoRepository.deleteById(userInfo.getUserid());
    }
}
