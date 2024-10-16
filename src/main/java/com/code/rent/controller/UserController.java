package com.code.rent.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.code.rent.common.Result;
import com.code.rent.constants.RedisConstants;
import com.code.rent.entity.User;
import com.code.rent.entity.dto.LoginParam;
import com.code.rent.entity.dto.UserDTO;
import com.code.rent.entity.vo.UserInfo;
import com.code.rent.service.UserService;
import com.code.rent.utils.EmailUtils;
import com.code.rent.utils.PasswordUtils;
import com.code.rent.utils.RedisUtil;
import com.code.rent.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// //    @Tag(name = "用户模块")
@RestController
@SaCheckLogin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private RedisUtil redisUtil;

//    //    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginParam login){
        return Result.success(userService.login(login));
    }

//    //    @Operation(summary = "退出登录")
    @SaIgnore
    @GetMapping("/logout")
    public Result<String> logout() {
        StpUtil.logout(StpUtil.getLoginId());
        return Result.success();
    }

//    //    @Operation(summary = "修改个人信息")
    @PutMapping("/updateInfo")
    public Result<String> updateInfo(@RequestBody UserDTO dto){
        User user = UserDTO.toPo(dto);
        // 更新缓存
        redisUtil.set(RedisConstants.USER,user.getId(),user);
        user.setPassword(PasswordUtils.encrypt(user.getPassword()));
        return Result.isSuccess(userService.updateById(user));
    }

//    //    @Operation(summary = "发送验证码")
    @SaIgnore
    @GetMapping("/sendCode")
    public Result sendCode(@RequestParam("email") String email){
        EmailUtils.isValidEmail(email);
        String code = ValidateCodeUtils.generateValidateCodeUtils(6).toString();
        // 调用邮箱服务发送验证码
        emailUtils.sendMailMessage(email,code);
        // 将生成的验证码存入Redis
        redisUtil.set(RedisConstants.EMAIL_CODE,email,code);
        return Result.success();
    }

//    //    @Operation(summary = "注册账号")
    @SaIgnore
    @PostMapping("/regist")
    public Result regist(@RequestBody UserDTO dto, @RequestParam("code") String code){
        return Result.isSuccess(userService.regist(dto,code));
    }

//    //    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public Result<UserInfo> getUserInfo(){
        String id = StpUtil.getLoginIdAsString();
        User user = (User) redisUtil.get(RedisConstants.USER.getKey() + id);
        String role = userService.getUserRole(user);
        UserInfo info = new UserInfo();
        info.setName(user.getUserName());
        info.setRole(role);
        info.setId(id);
        info.setAvatar(user.getAvater());
        return Result.success(info);
    }
}
