package com.code.rent.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.rent.constants.CodeEnum;
import com.code.rent.entity.User;
import com.code.rent.entity.dto.LoginParam;
import com.code.rent.exception.CustomException;
import com.code.rent.mapper.UserMapper;
import com.code.rent.service.UserService;
import com.code.rent.utils.PasswordUtils;
import org.springframework.stereotype.Service;

/**
* @author HeXin
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-10-29 14:58:39
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public String login(LoginParam login) {
        String username = login.getUsername();
        String password = login.getPassword();
        User user = getUserByName(username);
        if(user == null){
            throw new CustomException(CodeEnum.USER_NOT_FOUND);
        }
        if(PasswordUtils.match(password,user.getPassword())){
            throw new CustomException(CodeEnum.PASSWORD_ERROR);
        }
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Override
    public User getUserByName(String username) {
        return lambdaQuery().select(User::getId,User::getUserName,User::getPassword).eq(User::getUserName,username).one();
    }

}




