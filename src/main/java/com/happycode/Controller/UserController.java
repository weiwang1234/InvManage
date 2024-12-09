package com.happycode.Controller;

import com.happycode.model.UserInfo;
import com.happycode.repository.UserInfoRepository;
import com.happycode.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;  // 导入 ResponseEntity

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    // 查询所有用户
    @PostMapping("/getAll")
    public List<UserInfo> getAllUsers(@RequestBody(required = false) Object params) {
        // 如果需要参数传递，可以从 params 中提取（例如分页、过滤条件等）
        return userInfoRepository.findByStatus("1");
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

    // 删除用户 改为软删除
    @PostMapping("/delete")
    public void deleteUser(@RequestBody UserInfo userInfo) {

        UserInfo userinfo  =  userInfoRepository.findById(userInfo.getUserid()).orElse(null);
        userinfo.setStatus("2");
        userInfoRepository.save(userinfo);
        //userInfoRepository.deleteById(userInfo.getUserid());
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserInfo userInfo) {
        // 根据用户名查询用户
        UserInfo user = userInfoRepository.findByloginid(userInfo.getLoginid());

        if (user != null && user.getPassword().equals(userInfo.getPassword()) && user.getStatus().equals("1")) {
            // 登录成功，生成 token
            String token = JwtUtil.generateToken(user.getUsername());

            // 构建返回的 Map，包含 token 和 username
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());

            // 返回 token 和 username
            return ResponseEntity.ok(response);
        } else {
            // 登录失败，返回失败信息
            return ResponseEntity.status(200).body(Collections.emptyMap());  // 400 错误
        }
    }
}
