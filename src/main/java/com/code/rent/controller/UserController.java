package com.code.rent.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.code.rent.common.Result;
import com.code.rent.entity.User;
import com.code.rent.entity.dto.LoginParam;
import com.code.rent.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户模块")
@RestController
@SaCheckLogin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginParam login){
        return Result.success(userService.login(login));
    }
    @Operation(summary = "根据id查询用户")
    @GetMapping("/{id}")
    @SaCheckRole("admin")
    public Result<User> getById(@PathVariable Long id){
        return Result.success(userService.getById(id));
    }
    @Operation(summary = "增加用户")
    @PostMapping
    @SaCheckRole("admin")
    public Result create(@RequestBody User user){
        return Result.isSuccess(userService.save(user));
    }
    @Operation(summary = "修改用户")
    @PutMapping
    @SaCheckRole("admin")
    public Result update(@RequestBody User user){
        return Result.isSuccess(userService.updateById(user));
    }
}
