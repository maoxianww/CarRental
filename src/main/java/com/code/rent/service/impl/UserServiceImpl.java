package com.code.rent.service.impl;

import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.rent.constants.CodeEnum;
import com.code.rent.constants.RedisConstants;
import com.code.rent.entity.User;
import com.code.rent.entity.dto.LoginParam;
import com.code.rent.entity.dto.UserDTO;
import com.code.rent.entity.vo.UserVO;
import com.code.rent.exception.CustomException;
import com.code.rent.mapper.UserMapper;
import com.code.rent.service.UserService;
import com.code.rent.utils.PasswordUtils;
import com.code.rent.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author HeXin
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-10-29 14:58:39
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String login(LoginParam login) {
        String username = login.getUsername();
        String password = login.getPassword();
        User user = getUserByName(username);
        if(user == null){
            throw new CustomException(CodeEnum.USER_NOT_FOUND);
        }
        if(!PasswordUtils.match(password,user.getPassword())){
            throw new CustomException(CodeEnum.PASSWORD_ERROR);
        }
        StpUtil.login(user.getId());
        redisUtil.set(RedisConstants.USER,user.getId(),user);
        getUserRole(user);
        getUserPermission(user);
        return StpUtil.getTokenValue();
    }

    @Override
    public User getUserByName(String username) {
        return lambdaQuery().select(User::getId,User::getUserName,User::getPassword,User::getType).eq(User::getUserName,username).one();
    }

    @Override
    public String loginAdmin(LoginParam param) {
        String username = param.getUsername();
        String password = param.getPassword();
        User user = getUserByName(username);
        if(user == null){
            throw new CustomException(CodeEnum.USER_NOT_FOUND);
        }
        if(PasswordUtils.match(password,user.getPassword())){
            throw new CustomException(CodeEnum.PASSWORD_ERROR);
        }
        if(user.getType()!=0){
            throw new NotRoleException("请使用管理员账号登录！");
        }
        StpUtil.login(user.getId());
        redisUtil.set(RedisConstants.USER,user.getId(),user);
        getUserRole(user);
        getUserPermission(user);
        return StpUtil.getTokenValue();
    }

    /**
      * 获取用户角色
      */
    @Override
    public String  getUserRole(User user){
        Integer type = user.getType();
        String role;
        switch (type) {
            case 0 -> role = "admin";
            case 1 -> role = "vip";
            case 2 -> role = "user";
            default -> throw new CustomException("系统异常！");
        }
        // 存入缓存
        redisUtil.set(RedisConstants.USER_ROLE,user.getId(),role);
        return role;
    }

    /**
     * 获取用户权限
     */
    @Override
    public String getUserPermission(User user) {
        Integer type = user.getType();
        String permission;
        switch (type) {
            case 0 -> permission = "*";
            case 1 -> permission = "vip-user";
            case 2 -> permission = "ordinary-user";
            default -> throw new CustomException("系统异常！");
        }
        // 存入缓存
        redisUtil.set(RedisConstants.USER_PERMISSION, user.getId(),permission);
        return permission;
    }

    @Override
    public Boolean regist(UserDTO dto, String code) {
        // 从redis中拿去验证码
        if(redisUtil.getTime(RedisConstants.EMAIL_CODE.getKey() + dto.getEmail())==0){
            throw new CustomException(CodeEnum.CODE_EXPIRED);
        }
        if(!code.equals(redisUtil.get(RedisConstants.EMAIL_CODE.getKey()+dto.getEmail()).toString())){
            throw new CustomException(CodeEnum.CODE_ERROR);
        }
        User user = UserDTO.toPo(dto);
        user.setPassword(PasswordUtils.encrypt(user.getPassword()));
        return save(user);
    }

}




